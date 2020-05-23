package org.jhapy.backend.repository.graphdb.reference;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.jhapy.backend.domain.graphdb.reference.Country;
import org.jhapy.backend.domain.graphdb.reference.IntermediateRegion;
import org.jhapy.backend.domain.graphdb.reference.Region;
import org.jhapy.backend.domain.graphdb.reference.SubRegion;
import org.jhapy.baseserver.repository.graphdb.BaseRepository;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-16
 */
public interface CountryRepository extends BaseRepository<Country> {

  @Query(value = "CALL db.index.fulltext.queryNodes('Country-Trl', {name}) YIELD node RETURN node",
      countQuery = "CALL db.index.fulltext.queryNodes('Country-Trl', {name}) YIELD node RETURN count(node)")
  Page<Country> findByNameLike(String name, Pageable pageable);

  @Query(value = "CALL db.index.fulltext.queryNodes('Country-Trl', {name}) YIELD node RETURN count(node)")
  long countByNameLike(String name);

  @Query(value =
      "CALL db.index.fulltext.queryNodes('Country-Trl', {name}) YIELD node "
          + " MATCH (m:Country)"
          + " OPTIONAL MATCH (m:Country)-[rr:HAS_REGION]-(r:Region)"
          + " OPTIONAL MATCH (m:Country)-[rs:HAS_SUB_REGION]-(s:SubRegion)"
          + " OPTIONAL MATCH (m:Country)-[ri:HAS_INTERMEDIATE_REGION]-(i:IntermediateRegion)"
          + " WHERE m.iso2  =~ {iso2} OR m.iso3  =~ {iso3} OR id(node) = id(m) RETURN m, rr, r, rs, s, ri, i",
      countQuery = "CALL db.index.fulltext.queryNodes('Country-Trl', {name}) YIELD node "
          + " MATCH (m:Country)"
          + " WHERE m.iso2  =~ {iso2} OR m.iso3  =~ {iso3} OR id(node) = id(m) RETURN count(m)")
  Page<Country> findByNameLikeOrIso2LikeOrIso3Like(String name, String iso2, String iso3,
      Pageable pageable);

  @Query("CALL db.index.fulltext.queryNodes('Country-Trl', {name}) YIELD node "
      + " MATCH (m:Country)"
      + " WHERE m.iso2  =~ {iso2} OR m.iso3  =~ {iso3} OR id(node) = id(m) RETURN count(m)")
  long countByNameLikeOrIso2LikeOrIso3Like(String name, String iso2, String iso3);

  @Query(value = "MATCH (m:Country) "
      + " OPTIONAL MATCH (m:Country)-[rr:HAS_REGION]-(r:Region)"
      + " OPTIONAL MATCH (m:Country)-[rs:HAS_SUB_REGION]-(s:SubRegion)"
      + " OPTIONAL MATCH (m:Country)-[ri:HAS_INTERMEDIATE_REGION]-(i:IntermediateRegion)"
      + " RETURN m, rr, r, rs, s, ri, i",
      countQuery = "MATCH (m:Country) RETURN count(m)")
  Page<Country> findAll(Pageable pageable);

  @Query("MATCH (m:Country) RETURN count(m)")
  long count();

  Optional<Country> getByIso2(String iso2);

  Optional<Country> getByIso3(String iso3);

  List<Country> findByRegion(Region region);

  List<Country> findBySubRegion(SubRegion subRegion);

  List<Country> findByIntermediateRegion(IntermediateRegion intermediateRegion);

  @Query("MATCH (m:Country) WHERE id(m) = {id} RETURN m")
  Country getById(Long id);

}
