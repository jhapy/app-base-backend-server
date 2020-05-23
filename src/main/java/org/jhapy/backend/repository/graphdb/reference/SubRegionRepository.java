package org.jhapy.backend.repository.graphdb.reference;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.jhapy.backend.domain.graphdb.reference.SubRegion;
import org.jhapy.baseserver.repository.graphdb.BaseRepository;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-16
 */
public interface SubRegionRepository extends BaseRepository<SubRegion> {

  @Query(value =
      "CALL db.INDEX.fulltext.queryNodes('SubRegion-Trl', {name}) YIELD node RETURN node",
      countQuery = "CALL db.INDEX.fulltext.queryNodes('SubRegion-Trl', {name}) YIELD node RETURN count(node)")
  Page<SubRegion> findByName(String name, Pageable pageable);

  @Query("CALL db.INDEX.fulltext.queryNodes('SubRegion-Trl', {name}) YIELD node RETURN count(node)")
  long countByName(String name);

  @Query("MATCH (m:SubRegion),(c:Country) WHERE id(m) = {subRegionId} AND id(c) = {countryId}"
      + " CREATE (m)-[r:HAS_COUNTRIES]->(c) RETURN m")
  SubRegion addCountryToSubRegion(Long subRegionId, Long countryId);
}
