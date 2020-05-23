package org.jhapy.backend.repository.graphdb.reference;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.jhapy.backend.domain.graphdb.reference.Region;
import org.jhapy.baseserver.repository.graphdb.BaseRepository;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-16
 */
public interface RegionRepository extends BaseRepository<Region> {

  @Query(value =
      "CALL db.INDEX.fulltext.queryNodes('Region-Trl', {name}) YIELD node RETURN node",
      countQuery = "CALL db.INDEX.fulltext.queryNodes('Region-Trl', {name}) YIELD node RETURN count(node)")
  Page<Region> findByName(String name, Pageable pageable);

  @Query("CALL db.INDEX.fulltext.queryNodes('Region-Trl', {name}) YIELD node RETURN count(node)")
  long countByName(String name);

  @Query("MATCH (m:Region),(c:Country) WHERE id(m) = {regionId} AND id(c) = {countryId}"
      + " CREATE (m)-[r:HAS_COUNTRIES]->(c) RETURN m")
  Region addCountryToRegion(Long regionId, Long countryId);
}
