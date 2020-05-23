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
import org.jhapy.backend.domain.graphdb.reference.IntermediateRegion;
import org.jhapy.backend.repository.graphdb.reference.IntermediateRegionRepository;
import org.jhapy.commons.utils.HasLogger;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-27
 */
@Transactional(readOnly = true, transactionManager = "graphdbTransactionManager")
@Service
public class IntermediateRegionServiceImpl implements IntermediateRegionService, HasLogger {

  private final IntermediateRegionRepository intermediateRegionRepository;

  public IntermediateRegionServiceImpl(IntermediateRegionRepository intermediateRegionRepository) {
    this.intermediateRegionRepository = intermediateRegionRepository;
  }

  @Override
  public List<IntermediateRegion> findAll() {
    return StreamSupport.stream(intermediateRegionRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  @Override
  public Page<IntermediateRegion> findAnyMatching(String filter, String iso3Language,
      Pageable pageable) {
    if (StringUtils.isNotBlank(filter)) {
      return intermediateRegionRepository.findByName(filter, pageable);
    } else {
      return intermediateRegionRepository.findAll(pageable);
    }
  }

  @Override
  public long countAnyMatching(String filter, String iso3Language) {
    if (StringUtils.isNotBlank(filter)) {
      return intermediateRegionRepository.countByName(filter);
    } else {
      return intermediateRegionRepository.count();
    }
  }

  @Override
  public IntermediateRegion getById(Long id) {
    return intermediateRegionRepository.findById(id).get();
  }

  @Override
  public Neo4jRepository<IntermediateRegion, Long> getRepository() {
    return intermediateRegionRepository;
  }
}

