/*
 * Copyright 2020-2020 the original author or authors from the JHapy project.
 *
 * This file is part of the JHapy project, see https://www.jhapy.org/ for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jhapy.backend.service.reference;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jhapy.backend.config.AppProperties;
import org.jhapy.backend.domain.graphdb.reference.Country;
import org.jhapy.backend.domain.graphdb.reference.IntermediateRegion;
import org.jhapy.backend.domain.graphdb.reference.Region;
import org.jhapy.backend.domain.graphdb.reference.SubRegion;
import org.jhapy.backend.repository.graphdb.reference.CountryRepository;
import org.jhapy.backend.repository.graphdb.reference.IntermediateRegionRepository;
import org.jhapy.backend.repository.graphdb.reference.RegionRepository;
import org.jhapy.backend.repository.graphdb.reference.SubRegionRepository;
import org.jhapy.commons.utils.HasLogger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-27
 */
@Service
@Transactional(readOnly = true, transactionManager = "transactionManager")
public class CountryServiceImpl implements CountryService, HasLogger {

  private final AppProperties appProperties;
  private final CountryRepository countryRepository;
  private final RegionRepository regionRepository;
  private final SubRegionRepository subRegionRepository;
  private final IntermediateRegionRepository intermediateRegionRepository;

  private final Neo4jTemplate neo4jTemplate;

  private boolean hasBootstrapped = false;

  public CountryServiceImpl(
      AppProperties appProperties, CountryRepository countryRepository,
      RegionRepository regionRepository,
      SubRegionRepository subRegionRepository,
      IntermediateRegionRepository intermediateRegionRepository, Neo4jTemplate neo4jTemplate) {
    this.appProperties = appProperties;
    this.countryRepository = countryRepository;
    this.regionRepository = regionRepository;
    this.subRegionRepository = subRegionRepository;
    this.intermediateRegionRepository = intermediateRegionRepository;
    this.neo4jTemplate = neo4jTemplate;
  }

  @Override
  public Country getById(Long id) {
    return countryRepository.findById(id).orElse(null);
  }

  @Override
  public Page<Country> findAnyMatching(String filter, Boolean showInactive, Pageable pageable) {
    if (StringUtils.isNotBlank(filter)) {
      String filterExpr = filter + "*";
      Sort sort = pageable.getSort();
      Sort newSort = Sort.unsorted();
      if ( sort.isSorted() ) {
        Sort.Order dataSort = sort.iterator().next();
        newSort = Sort.by(dataSort.getDirection(), "node.`" + dataSort.getProperty() + "`");
      }
      PageRequest newPageRequest = PageRequest
          .of(pageable.getPageNumber(), pageable.getPageSize(), newSort);
      return countryRepository
          .findByNameLikeOrIso2LikeOrIso3Like(filterExpr, filterExpr, filterExpr, newPageRequest);
    } else {
      if (pageable.getSort().isSorted() && pageable.getSort().iterator().next().getProperty().startsWith("name")) {
        Sort sort = pageable.getSort();
        Sort.Order dataSort = sort.iterator().next();
        Sort newSort = Sort.by(dataSort.getDirection(), "m.`" + dataSort.getProperty() + "`");
        PageRequest newPageRequest = PageRequest
            .of(pageable.getPageNumber(), pageable.getPageSize(), newSort);
        return countryRepository.findAll(newPageRequest);
      } else {
        return countryRepository.findAll(pageable);
      }
    }
  }

  @Override
  public long countAnyMatching(String filter, Boolean showInactive) {
    if (!hasBootstrapped) {
      bootstrapCountries();
    }
    if (StringUtils.isNotBlank(filter)) {
      String filterExpr = filter + "*";
      return countryRepository
          .countByNameLikeOrIso2LikeOrIso3Like(filterExpr, filterExpr, filterExpr);
    } else {
      return countryRepository.count();
    }
  }

  @Override
  public Country getByIso2OrIso3(String iso2OrIso3Name) {
    Optional<Country> _country = countryRepository.getByIso3(iso2OrIso3Name);
    if (_country.isEmpty()) {
      _country = countryRepository.getByIso2(iso2OrIso3Name);
      if (_country.isEmpty()) {
        return null;
      }
    }
    return _country.get();
  }

  @Transactional
  @PostConstruct
  protected void postLoad() {
    bootstrapCountries();
  }

  @CacheEvict(value = {"countryTrl", "intermediateRegionTrl", "regionTrl",
      "subRegionTrl"}, allEntries = true)
  @Transactional
  public void bootstrapCountries() {
    if (hasBootstrapped || !appProperties.getBootstrap().getIso3166().isEnabled()) {
      return;
    }
    var loggerPrefix = getLoggerPrefix("bootstrapCountries");

    String language = "en";
    try (Workbook workbook = WorkbookFactory
        .create(new File(appProperties.getBootstrap().getIso3166().getFile()))) {

      Sheet sheet = workbook.getSheetAt(0);

      logger().info(loggerPrefix + sheet.getPhysicalNumberOfRows() + " rows");

      Iterator<Row> rowIterator = sheet.rowIterator();
      int rowIndex = 0;
      while (rowIterator.hasNext()) {
        Row row = rowIterator.next();

        if (rowIndex == 0) {
          rowIndex++;
          continue;
        }
        rowIndex++;

        if (rowIndex % 10 == 0) {
          logger().info(loggerPrefix + "Handle row " + rowIndex);
        }

        Cell nameCell = row.getCell(0);
        Cell iso2Cell = row.getCell(1);
        Cell iso3Cell = row.getCell(2);
        Cell regionCell = row.getCell(3);
        Cell subRegionCell = row.getCell(4);
        Cell intermediateRegionCell = row.getCell(5);

        String nameCellValue = nameCell.getStringCellValue();
        if (StringUtils.isNotBlank(nameCellValue)) {
          Country country = null;
          Optional<Country> c = neo4jTemplate
              .findOne("MATCH (m:Country {`names." + language + ".value`: $value}) RETURN m",
                  Collections.singletonMap("value", nameCellValue), Country.class);

          if (c.isEmpty()) {
            country = new Country();
            country.setIso2(iso2Cell.getStringCellValue());
            country.setIso3(iso3Cell.getStringCellValue());
            country.getNames().setTranslation(language, nameCellValue);
            country = countryRepository.save(country);
          } else {
            country = c.get();
          }

          if (regionCell != null) {
            String regionCellValue = regionCell.getStringCellValue();
            if (StringUtils.isNotBlank(regionCellValue)) {
              Region region = null;

              Optional<Region> r = neo4jTemplate.findOne(
                  "MATCH (m:Region {`names." + language
                      + ".value`: $value})-[r:HAS_COUNTRIES]-(c:Country) RETURN m, collect(r),collect(c)",
                  Collections
                      .singletonMap("value", regionCellValue), Region.class);

              if (r.isEmpty()) {
                region = new Region();
                region.getNames().setTranslation(language, regionCellValue);
                region = regionRepository.save(region);
              } else {
                region = r.get();
              }

              if (!region.getCountries().contains(country)) {
                region = regionRepository.addCountryToRegion(region.getId(), country.getId());
                countryRepository.addRegionToCountry(country.getId(), region.getId());
              }

              if (subRegionCell != null) {
                String subRegionCellValue = subRegionCell.getStringCellValue();
                if (StringUtils.isNotBlank(subRegionCellValue)) {
                  SubRegion subRegion = null;

                  Optional<SubRegion> sr = neo4jTemplate.findOne(
                      "MATCH (m:SubRegion {`names." + language
                          + ".value`: $value})-[r:HAS_COUNTRIES]-(c:Country) RETURN m, collect(r),collect(c)",
                      Collections
                          .singletonMap("value", subRegionCellValue), SubRegion.class);
                  if (sr.isEmpty()) {
                    subRegion = new SubRegion();
                    subRegion.getNames().setTranslation(language, subRegionCellValue);
                    subRegion = subRegionRepository.save(subRegion);
                  } else {
                    subRegion = sr.get();
                  }

                  if (!subRegion.getCountries().contains(country)) {
                    subRegion = subRegionRepository
                        .addCountryToSubRegion(subRegion.getId(), country.getId());
                    countryRepository.addSubRegionToCountry(country.getId(), subRegion.getId());
                  }

                  if (intermediateRegionCell != null) {
                    String intermediateRegionCellValue = intermediateRegionCell
                        .getStringCellValue();
                    if (StringUtils.isNotBlank(intermediateRegionCellValue)) {
                      IntermediateRegion intermediateRegion = null;

                      Optional<IntermediateRegion> ir = neo4jTemplate.findOne(
                          "MATCH (m:IntermediateRegion {`names." + language
                              + ".value`: $value}) RETURN m", Collections
                              .singletonMap("value", intermediateRegionCellValue),
                          IntermediateRegion.class);
                      if (ir.isEmpty()) {
                        intermediateRegion = new IntermediateRegion();
                        intermediateRegion.getNames()
                            .setTranslation(language, intermediateRegionCellValue);
                        intermediateRegion = intermediateRegionRepository.save(intermediateRegion);
                      } else {
                        intermediateRegion = ir.get();
                      }

                      if (!intermediateRegion.getCountries().contains(country)) {
                        intermediateRegion = intermediateRegionRepository
                            .addCountryToIntermediateRegion(intermediateRegion.getId(),
                                country.getId());
                        countryRepository.addIntermediateRegionToCountry(country.getId(),
                            intermediateRegion.getId());
                      }
                      if (!intermediateRegion.getRegions().contains(region)) {
                        intermediateRegion = intermediateRegionRepository
                            .addRegionToIntermediateRegion(intermediateRegion.getId(),
                                region.getId());
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }

    } catch (IOException ioe) {
      logger().error(loggerPrefix + "Unexpected error : " + ioe.getLocalizedMessage(), ioe);
    }
    logger().info(loggerPrefix + "Done");
    hasBootstrapped = true;
  }

  @Override
  public Neo4jRepository<Country, Long> getRepository() {
    return countryRepository;
  }
}
