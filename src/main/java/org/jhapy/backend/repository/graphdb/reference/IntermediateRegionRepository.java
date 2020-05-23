package org.jhapy.backend.repository.graphdb.reference;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.jhapy.backend.domain.graphdb.reference.IntermediateRegion;
import org.jhapy.baseserver.repository.graphdb.BaseRepository;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-16
 */
public interface IntermediateRegionRepository extends BaseRepository<IntermediateRegion> {

  @Query(value =
      "CALL db.INDEX.fulltext.queryNodes('IntermediateRegion-Trl', {name}) YIELD node RETURN node",
      countQuery = "CALL db.INDEX.fulltext.queryNodes('IntermediateRegion-Trl', {name}) YIELD node RETURN count(node)")
  Page<IntermediateRegion> findByName(String name, Pageable pageable);

  @Query("CALL db.INDEX.fulltext.queryNodes('IntermediateRegion-Trl', {name}) YIELD node RETURN count(node)")
  long countByName(String name);

  @Query(
      "MATCH (m:IntermediateRegion),(c:Country) WHERE id(m) = {intermediateRegionId} AND id(c) = {countryId}"
          + " CREATE (m)-[r:HAS_COUNTRIES]->(c) RETURN m")
  IntermediateRegion addCountryToIntermediateRegion(Long intermediateRegionId, Long countryId);

  @Query(
      "MATCH (m:IntermediateRegion),(c:Region) WHERE id(m) = {intermediateRegionId} AND id(c) = {regionId}"
          + " CREATE (m)-[r:HAS_REGIONS]->(c) RETURN m")
  IntermediateRegion addRegionToIntermediateRegion(Long intermediateRegionId, Long regionId);
}
