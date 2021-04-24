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

package org.jhapy.backend.domain.graphdb.reference;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jhapy.baseserver.domain.graphdb.BaseEntity;
import org.jhapy.baseserver.domain.graphdb.EntityTranslations;
import org.jhapy.baseserver.utils.NameTranslationConverter;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Node
public class IntermediateRegion extends BaseEntity {

  @ConvertWith(converter = NameTranslationConverter.class)
  private EntityTranslations names = new EntityTranslations();

  @Relationship
  private SubRegion subRegion;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @Relationship("HAS_REGIONS")
  private Set<Region> regions = new HashSet<>();

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @Relationship("HAS_COUNTRIES")
  private Set<Country> countries = new HashSet<>();

  @EqualsAndHashCode.Include
  @ToString.Include
  private String subRegion() {
    return (subRegion != null && subRegion.getId() != null) ? subRegion.getId().toString() : null;
  }
}
