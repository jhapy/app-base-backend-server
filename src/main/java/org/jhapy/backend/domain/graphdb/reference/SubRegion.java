package org.jhapy.backend.domain.graphdb.reference;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.jhapy.baseserver.domain.graphdb.BaseEntity;
import org.jhapy.baseserver.domain.graphdb.EntityTranslations;
import org.jhapy.baseserver.utils.NameTranslationConverter;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NodeEntity
public class SubRegion extends BaseEntity {

  @Convert(NameTranslationConverter.class)
  private EntityTranslations names = new EntityTranslations();

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @Relationship
  private Region region;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @Relationship("HAS_COUNTRIES")
  private Set<Country> countries = new HashSet<>();

  @EqualsAndHashCode.Include
  @ToString.Include
  private String region() {
    return (region != null && region.getId() != null) ? region.getId().toString() : null;
  }
}
