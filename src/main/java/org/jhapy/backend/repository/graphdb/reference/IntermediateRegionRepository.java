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

package org.jhapy.backend.repository.graphdb.reference;

import org.jhapy.backend.domain.graphdb.reference.IntermediateRegion;
import org.jhapy.baseserver.repository.graphdb.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-16
 */
public interface IntermediateRegionRepository extends BaseRepository<IntermediateRegion> {

  @Query(
      value =
          "CALL db.INDEX.fulltext.queryNodes('IntermediateRegion-Trl', $name) YIELD node RETURN node :#{orderBy(#pageable)} SKIP $skip LIMIT $limit",
      countQuery =
          "CALL db.INDEX.fulltext.queryNodes('IntermediateRegion-Trl', $name) YIELD node RETURN count(node)")
  Page<IntermediateRegion> findByName(@Param("name") String name, Pageable pageable);

  @Query(
      "CALL db.INDEX.fulltext.queryNodes('IntermediateRegion-Trl', $name) YIELD node RETURN count(node)")
  long countByName(@Param("name") String name);

  @Query(
      "MATCH (m:IntermediateRegion) WITH m MATCH (c:Country) WHERE id(m) = $intermediateRegionId AND id(c) = $countryId"
          + " CREATE (m)-[r:HAS_COUNTRIES]->(c) RETURN m")
  IntermediateRegion addCountryToIntermediateRegion(
      @Param("intermediateRegionId") UUID intermediateRegionId, @Param("countryId") UUID countryId);

  @Query(
      "MATCH (m:IntermediateRegion) WITH m MATCH (c:Region) WHERE id(m) = $intermediateRegionId AND id(c) = $regionId"
          + " CREATE (m)-[r:HAS_REGIONS]->(c) RETURN m")
  IntermediateRegion addRegionToIntermediateRegion(
      @Param("intermediateRegionId") UUID intermediateRegionId, @Param("regionId") UUID regionId);
}