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

import org.apache.commons.lang3.StringUtils;
import org.jhapy.backend.domain.graphdb.reference.IntermediateRegion;
import org.jhapy.backend.repository.graphdb.reference.IntermediateRegionRepository;
import org.jhapy.commons.utils.HasLogger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    return intermediateRegionRepository.findAll().stream().collect(Collectors.toList());
  }

  @Override
  public Page<IntermediateRegion> findAnyMatching(
      String filter, Boolean showInactive, Pageable pageable) {
    if (StringUtils.isNotBlank(filter)) {
      return intermediateRegionRepository.findByName(filter, pageable);
    } else {
      return intermediateRegionRepository.findAll(pageable);
    }
  }

  @Override
  public long countAnyMatching(String filter, Boolean showInactive) {
    if (StringUtils.isNotBlank(filter)) {
      return intermediateRegionRepository.countByName(filter);
    } else {
      return intermediateRegionRepository.count();
    }
  }

  @Override
  public IntermediateRegion getById(UUID id) {
    return intermediateRegionRepository.findById(id).get();
  }

  @Override
  public Neo4jRepository<IntermediateRegion, UUID> getRepository() {
    return intermediateRegionRepository;
  }
}