package org.jhapy.backend.converter;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.jhapy.backend.domain.graphdb.reference.Country;
import org.jhapy.backend.domain.graphdb.reference.IntermediateRegion;
import org.jhapy.backend.domain.graphdb.reference.Region;
import org.jhapy.backend.domain.graphdb.reference.SubRegion;
import org.jhapy.commons.utils.OrikaBeanMapper;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-06-05
 */
@Component
public class ReferenceConverter {

  private final OrikaBeanMapper orikaBeanMapper;

  public ReferenceConverter(OrikaBeanMapper orikaBeanMapper) {
    this.orikaBeanMapper = orikaBeanMapper;
  }

  @Bean
  public void configure() {
    orikaBeanMapper
        .getClassMapBuilder(Country.class, org.jhapy.dto.domain.reference.Country.class)
        .byDefault().customize(
        new CustomMapper<Country, org.jhapy.dto.domain.reference.Country>() {
          @Override
          public void mapAtoB(Country domain, org.jhapy.dto.domain.reference.Country dto,
              MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              dto.setName(domain.getNames().getTranslation(iso3Language));
            }
          }

          @Override
          public void mapBtoA(org.jhapy.dto.domain.reference.Country dto, Country domain,
              MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              domain.getNames().setTranslation(iso3Language, dto.getName());
            }
          }
        }).register();

    orikaBeanMapper.getClassMapBuilder(IntermediateRegion.class,
        org.jhapy.dto.domain.reference.IntermediateRegion.class).byDefault()
        .exclude("translations").customize(
        new CustomMapper<IntermediateRegion, org.jhapy.dto.domain.reference.IntermediateRegion>() {
          @Override
          public void mapAtoB(IntermediateRegion domain,
              org.jhapy.dto.domain.reference.IntermediateRegion dto,
              MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              dto.setName(domain.getNames().getTranslation(iso3Language));
            }
          }

          @Override
          public void mapBtoA(org.jhapy.dto.domain.reference.IntermediateRegion dto,
              IntermediateRegion domain,
              MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              domain.getNames().setTranslation(iso3Language, dto.getName());
            }
          }
        }).register();

    orikaBeanMapper
        .getClassMapBuilder(Region.class, org.jhapy.dto.domain.reference.Region.class)
        .byDefault().customize(
        new CustomMapper<Region, org.jhapy.dto.domain.reference.Region>() {
          @Override
          public void mapAtoB(Region domain, org.jhapy.dto.domain.reference.Region dto,
              MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              dto.setName(domain.getNames().getTranslation(iso3Language));
            }
          }

          @Override
          public void mapBtoA(org.jhapy.dto.domain.reference.Region dto, Region domain,
              MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              domain.getNames().setTranslation(iso3Language, dto.getName());
            }
          }
        }).register();

    orikaBeanMapper
        .getClassMapBuilder(SubRegion.class,
            org.jhapy.dto.domain.reference.SubRegion.class)
        .byDefault().customize(
        new CustomMapper<SubRegion, org.jhapy.dto.domain.reference.SubRegion>() {
          @Override
          public void mapAtoB(SubRegion domain,
              org.jhapy.dto.domain.reference.SubRegion dto,
              MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              dto.setName(domain.getNames().getTranslation(iso3Language));
            }
          }

          @Override
          public void mapBtoA(org.jhapy.dto.domain.reference.SubRegion dto,
              SubRegion domain,
              MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              domain.getNames().setTranslation(iso3Language, dto.getName());
            }
          }
        }).register();
  }
}
