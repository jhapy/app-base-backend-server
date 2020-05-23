package org.jhapy.backend.service.reference;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jhapy.backend.domain.graphdb.reference.Region;
import org.jhapy.backend.repository.graphdb.reference.RegionRepository;
import org.jhapy.commons.utils.HasLogger;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-27
 */
@Transactional(readOnly = true, transactionManager = "graphdbTransactionManager")
@Service
public class RegionServiceImpl implements RegionService, HasLogger {

  private final RegionRepository regionRepository;

  public RegionServiceImpl(RegionRepository regionRepository) {
    this.regionRepository = regionRepository;
  }

  @Override
  public List<Region> findAll() {
    return StreamSupport.stream(regionRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  @Override
  public Page<Region> findAnyMatching(String filter, String iso3Language, Pageable pageable) {
    if (StringUtils.isNotBlank(filter)) {
      return regionRepository.findByName(filter, pageable);
    } else {
      return regionRepository.findAll(pageable);
    }
  }

  @Override
  public long countAnyMatching(String filter, String iso3Language) {
    if (StringUtils.isNotBlank(filter)) {
      return regionRepository.countByName(filter);
    } else {
      return regionRepository.count();
    }
  }

  @Override
  public Region getById(Long id) {
    return regionRepository.findById(id).get();
  }

  @Override
  public Neo4jRepository<Region, Long> getRepository() {
    return regionRepository;
  }
}
