package org.jhapy.backend.domain.graphdb.reference;

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
 * Referential for countries, based on the ISO 3166 list
 *
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NodeEntity
public class Country extends BaseEntity {

  @Convert(NameTranslationConverter.class)
  private EntityTranslations names = new EntityTranslations();

  /**
   * Alpha-2 code
   */
  private String iso2;

  /**
   * Alpha-3 code
   */
  private String iso3;

  /**
   * Dialing code prefix
   */
  private String dialingCode;

  /**
   * Is European Union
   */
  private Boolean isEU;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @Relationship("HAS_REGION")
  private Region region;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @Relationship("HAS_SUB_REGION")
  private SubRegion subRegion;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @Relationship("HAS_INTERMEDIATE_REGION")
  private IntermediateRegion intermediateRegion;

  @EqualsAndHashCode.Include
  @ToString.Include
  private String region() {
    return (region != null && region.getId() != null) ? region.getId().toString() : null;
  }

  @EqualsAndHashCode.Include
  @ToString.Include
  private String subRegion() {
    return (subRegion != null && subRegion.getId() != null) ? subRegion.getId().toString() : null;
  }

  @EqualsAndHashCode.Include
  @ToString.Include
  private String intermediateRegion() {
    return (intermediateRegion != null && intermediateRegion.getId() != null) ? intermediateRegion
        .getId().toString() : null;
  }
}
