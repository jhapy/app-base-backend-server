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

import java.util.List;
import java.util.Optional;
import org.jhapy.backend.domain.graphdb.reference.Country;
import org.jhapy.backend.domain.graphdb.reference.IntermediateRegion;
import org.jhapy.backend.domain.graphdb.reference.Region;
import org.jhapy.backend.domain.graphdb.reference.SubRegion;
import org.jhapy.baseserver.repository.graphdb.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-16
 */
public interface CountryRepository extends BaseRepository<Country> {

  @Query(value = "CALL db.index.fulltext.queryNodes('Country-Trl', $name) YIELD node RETURN node :#{orderBy(#pageable)} SKIP $skip LIMIT $limit",
      countQuery = "CALL db.index.fulltext.queryNodes('Country-Trl', $name) YIELD node RETURN count(node)")
  Page<Country> findByNameLike(@Param("name") String name, Pageable pageable);

  @Query(value = "CALL db.index.fulltext.queryNodes('Country-Trl', $name) YIELD node RETURN count(node)")
  long countByNameLike(@Param("name") String name);

  @Query(value =
      "CALL db.index.fulltext.queryNodes('Country-Trl', $name) YIELD node "
          + " MATCH (m:Country)"
          + " OPTIONAL MATCH (m:Country)-[rr:HAS_REGION]-(r:Region)"
          + " OPTIONAL MATCH (m:Country)-[rs:HAS_SUB_REGION]-(s:SubRegion)"
          + " OPTIONAL MATCH (m:Country)-[ri:HAS_INTERMEDIATE_REGION]-(i:IntermediateRegion)"
          + " WHERE m.iso2  =~ $iso2 OR m.iso3  =~ $iso3 OR id(node) = id(m) RETURN m, rr, r, rs, s, ri, i :#{orderBy(#pageable)} SKIP $skip LIMIT $limit",
      countQuery = "CALL db.index.fulltext.queryNodes('Country-Trl', $name) YIELD node "
          + " MATCH (m:Country)"
          + " WHERE m.iso2  =~ $iso2 OR m.iso3  =~ $iso3 OR id(node) = id(m) RETURN count(m)")
  Page<Country> findByNameLikeOrIso2LikeOrIso3Like(@Param("name") String name,
      @Param("iso2") String iso2, @Param("iso3") String iso3,
      Pageable pageable);

  @Query("CALL db.index.fulltext.queryNodes('Country-Trl', $name) YIELD node "
      + " MATCH (m:Country)"
      + " WHERE m.iso2  =~ $iso2 OR m.iso3  =~ $iso3 OR id(node) = id(m) RETURN count(m)")
  long countByNameLikeOrIso2LikeOrIso3Like(@Param("name") String name, @Param("iso2") String iso2,
      @Param("iso3") String iso3);

  @Query(value = "MATCH (m:Country) "
      + " OPTIONAL MATCH (m:Country)-[rr:HAS_REGION]-(r:Region)"
      + " OPTIONAL MATCH (m:Country)-[rs:HAS_SUB_REGION]-(s:SubRegion)"
      + " OPTIONAL MATCH (m:Country)-[ri:HAS_INTERMEDIATE_REGION]-(i:IntermediateRegion)"
      + " RETURN m, rr, r, rs, s, ri, i :#{orderBy(#pageable)} SKIP $skip LIMIT $limit",
      countQuery = "MATCH (m:Country) RETURN count(m)")
  Page<Country> findAll(Pageable pageable);

  @Query("MATCH (m:Country) RETURN count(m)")
  long count();

  Optional<Country> getByIso2(String iso2);

  Optional<Country> getByIso3(String iso3);

  List<Country> findByRegion(Region region);

  List<Country> findBySubRegion(SubRegion subRegion);

  List<Country> findByIntermediateRegion(IntermediateRegion intermediateRegion);

  @Query("MATCH (m:Country) WHERE id(m) = $id RETURN m")
  Country getById(@Param("id") Long id);

  @Query(
      "MATCH (m:Country) WITH m MATCH (c:IntermediateRegion) WHERE id(m) = $countryId AND id(c) = $intermediateRegionId"
          + " CREATE (m)-[r:HAS_INTERMEDIATE_REGION]->(c) RETURN m")
  Country addIntermediateRegionToCountry(@Param("countryId") Long countryId,
      @Param("intermediateRegionId") Long intermediateRegionId);

  @Query(
      "MATCH (m:Country) WITH m MATCH (c:Region) WHERE id(m) = $countryId AND id(c) = $regionId"
          + " CREATE (m)-[r:HAS_REGION]->(c) RETURN m")
  Country addRegionToCountry(@Param("countryId") Long countryId, @Param("regionId") Long regionId);

  @Query(
      "MATCH (m:Country) WITH m MATCH (c:SubRegion) WHERE id(m) = $countryId AND id(c) = $subRegionId"
          + " CREATE (m)-[r:HAS_SUB_REGION]->(c) RETURN m")
  Country addSubRegionToCountry(@Param("countryId") Long countryId,
      @Param("subRegionId") Long subRegionId);

}
