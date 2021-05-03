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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.StringUtils;
import org.jhapy.backend.domain.graphdb.reference.SubRegion;
import org.jhapy.backend.repository.graphdb.reference.SubRegionRepository;
import org.jhapy.commons.utils.HasLogger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-27
 */
@Transactional(readOnly = true, transactionManager = "graphdbTransactionManager")
@Service
public class SubRegionServiceImpl implements SubRegionService, HasLogger {

  private final SubRegionRepository subRegionRepository;

  public SubRegionServiceImpl(SubRegionRepository subRegionRepository) {
    this.subRegionRepository = subRegionRepository;
  }

  @Override
  public List<SubRegion> findAll() {
    return StreamSupport.stream(subRegionRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  @Override
  public Page<SubRegion> findAnyMatching(String filter, Boolean showInactive, Pageable pageable) {
    if (StringUtils.isNotBlank(filter)) {
      return subRegionRepository.findByName(filter, pageable);
    } else {
      return subRegionRepository.findAll(pageable);
    }
  }

  @Override
  public long countAnyMatching(String filter, Boolean showInactive) {
    if (StringUtils.isNotBlank(filter)) {
      return subRegionRepository.countByName(filter);
    } else {
      return subRegionRepository.count();
    }
  }

  @Override
  public SubRegion getById(Long id) {
    return subRegionRepository.findById(id).get();
  }

  @Override
  public Neo4jRepository<SubRegion, Long> getRepository() {
    return subRegionRepository;
  }
}
