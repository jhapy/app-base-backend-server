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

package org.jhapy.backend.converter;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.commons.lang3.StringUtils;
import org.jhapy.backend.domain.graphdb.reference.Country;
import org.jhapy.backend.domain.graphdb.reference.IntermediateRegion;
import org.jhapy.backend.domain.graphdb.reference.Region;
import org.jhapy.backend.domain.graphdb.reference.SubRegion;
import org.jhapy.commons.utils.OrikaBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
