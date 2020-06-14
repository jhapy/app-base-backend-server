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
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

  private final SessionFactory sessionFactory;

  private boolean hasBootstrapped = false;

  public CountryServiceImpl(
      AppProperties appProperties, CountryRepository countryRepository,
      RegionRepository regionRepository,
      SubRegionRepository subRegionRepository,
      IntermediateRegionRepository intermediateRegionRepository,
      SessionFactory sessionFactory) {
    this.appProperties = appProperties;
    this.countryRepository = countryRepository;
    this.regionRepository = regionRepository;
    this.subRegionRepository = subRegionRepository;
    this.intermediateRegionRepository = intermediateRegionRepository;
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Country getById(Long id) {
    return countryRepository.findById(id).get();
  }

  @Override
  public Page<Country> findAnyMatching(String filter, Pageable pageable) {
    if (StringUtils.isNotBlank(filter)) {
      String filterExpr = filter + "*";
      Sort sort = pageable.getSort();
      Sort.Order dataSort = sort.iterator().next();
      Sort newSort = Sort.by(dataSort.getDirection(), "node.`" + dataSort.getProperty() + "`");
      PageRequest newPageRequest = PageRequest
          .of(pageable.getPageNumber(), pageable.getPageSize(), newSort);
      return countryRepository
          .findByNameLikeOrIso2LikeOrIso3Like(filterExpr, filterExpr, filterExpr, newPageRequest);
    } else {
      if (pageable.getSort().iterator().next().getProperty().startsWith("name")) {
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
  public long countAnyMatching(String filter) {
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
    if (!_country.isPresent()) {
      _country = countryRepository.getByIso2(iso2OrIso3Name);
      if (!_country.isPresent()) {
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
    if (hasBootstrapped || !appProperties.getBootstrap().getIso3166().getIsEnabled()) {
      return;
    }
    String loggerPrefix = getLoggerPrefix("bootstrapCountries");

    String language = "en";
    try (Workbook workbook = WorkbookFactory
        .create(new File(appProperties.getBootstrap().getIso3166().getFile()))) {

      Session session = sessionFactory.openSession();

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

        String iso3CellCellValue = iso3Cell.getStringCellValue();
        if (StringUtils.isNotBlank(iso3CellCellValue)) {
          Country country = null;

          Iterable<Country> c = session.query(Country.class,
              "MATCH (m:Country {`name." + language + ".value`: {value}}) RETURN m", Collections
                  .singletonMap("value", iso3CellCellValue));
          if (c.iterator().hasNext()) {
            country = c.iterator().next();
          }

          if (country == null) {
            country = new Country();
            country.setIso2(iso2Cell.getStringCellValue());
            country.setIso3(iso3Cell.getStringCellValue());
            country.getNames().setTranslation(language, nameCell.getStringCellValue()
            );
            country = countryRepository.save(country);
          }

          if (regionCell != null) {
            String regionCellValue = regionCell.getStringCellValue();
            if (StringUtils.isNotBlank(regionCellValue)) {
              Region region = null;

              Iterable<Region> r = session.query(Region.class,
                  "MATCH (m:Region {`name." + language + ".value`: {value}}) RETURN m", Collections
                      .singletonMap("value", iso3CellCellValue));
              if (r.iterator().hasNext()) {
                region = r.iterator().next();
              }

              if (region == null) {
                region = new Region();
                region.getNames().setTranslation(language, regionCellValue);
                region = regionRepository.save(region);
              }

              if (!region.getCountries().contains(country)) {
                region = regionRepository.addCountryToRegion(region.getId(), country.getId());
              }
              country.setRegion(region);
              countryRepository.save(country);

              if (subRegionCell != null) {
                String subRegionCellValue = subRegionCell.getStringCellValue();
                if (StringUtils.isNotBlank(subRegionCellValue)) {
                  SubRegion subRegion = null;

                  Iterable<SubRegion> sr = session.query(SubRegion.class,
                      "MATCH (m:SubRegion {`name." + language + ".value`: {value}}) RETURN m",
                      Collections
                          .singletonMap("value", iso3CellCellValue));
                  if (sr.iterator().hasNext()) {
                    subRegion = sr.iterator().next();
                  }

                  if (subRegion == null) {
                    subRegion = new SubRegion();
                    subRegion.getNames().setTranslation(language, subRegionCellValue);
                    subRegion = subRegionRepository.save(subRegion);
                  }

                  if (!subRegion.getCountries().contains(country)) {
                    subRegion = subRegionRepository
                        .addCountryToSubRegion(subRegion.getId(), country.getId());
                  }
                  country.setSubRegion(subRegion);
                  countryRepository.save(country);

                  if (intermediateRegionCell != null) {
                    String intermediateRegionCellValue = intermediateRegionCell
                        .getStringCellValue();
                    if (StringUtils.isNotBlank(intermediateRegionCellValue)) {
                      IntermediateRegion intermediateRegion = null;

                      Iterable<IntermediateRegion> ir = session.query(IntermediateRegion.class,
                          "MATCH (m:IntermediateRegion {`name." + language
                              + ".value`: {value}}) RETURN m", Collections
                              .singletonMap("value", intermediateRegionCellValue));
                      if (ir.iterator().hasNext()) {
                        intermediateRegion = ir.iterator().next();
                      }

                      if (intermediateRegion == null) {
                        intermediateRegion = new IntermediateRegion();
                        intermediateRegion.getNames().setTranslation(language, subRegionCellValue);
                        intermediateRegion = intermediateRegionRepository.save(intermediateRegion);
                      }

                      if (!intermediateRegion.getCountries().contains(country)) {
                        intermediateRegion = intermediateRegionRepository
                            .addCountryToIntermediateRegion(intermediateRegion.getId(),
                                country.getId());
                      }
                      if (!intermediateRegion.getRegions().contains(region)) {
                        intermediateRegion = intermediateRegionRepository
                            .addRegionToIntermediateRegion(intermediateRegion.getId(),
                                region.getId());
                      }
                      country.setIntermediateRegion(intermediateRegion);
                      countryRepository.save(country);
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
