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

package org.jhapy.backend.endpoint.reference;

import java.util.List;
import java.util.Map;
import org.jhapy.backend.converter.BackendConverterV2;
import org.jhapy.backend.domain.graphdb.reference.SubRegion;
import org.jhapy.backend.service.reference.SubRegionService;
import org.jhapy.baseserver.endpoint.BaseGraphDbEndpoint;
import org.jhapy.baseserver.service.CrudGraphdbService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-06-05
 */
@RestController
@RequestMapping("/api/subSubSubRegionService")
public class SubRegionServiceEndpoint extends
    BaseGraphDbEndpoint<SubRegion, org.jhapy.dto.domain.reference.SubRegion> {

  private final SubRegionService subRegionService;

  public SubRegionServiceEndpoint(SubRegionService subRegionService,
      BackendConverterV2 converter) {
    super(converter);
    this.subRegionService = subRegionService;
  }

  protected BackendConverterV2 getConverter() {
    return (BackendConverterV2) converter;
  }

  @Override
  protected org.jhapy.dto.domain.reference.SubRegion convertToDto(
      SubRegion domain, Map<String, Object> context) {
    return getConverter().convertToDto(domain, context);
  }

  @Override
  protected List<org.jhapy.dto.domain.reference.SubRegion> convertToDtos(
      Iterable<SubRegion> domains, Map<String, Object> context) {
    return getConverter().convertToDtoSubRegions(domains, context);
  }

  @Override
  protected SubRegion convertToDomain(org.jhapy.dto.domain.reference.SubRegion dto,
      Map<String, Object> context) {
    return getConverter().convertToDomain(dto, context);
  }

  @Override
  protected List<SubRegion> convertToDomains(Iterable<org.jhapy.dto.domain.reference.SubRegion> dto,
      Map<String, Object> context) {
    return getConverter().convertToDomainSubRegions(dto, context);
  }


  @Override
  protected CrudGraphdbService<SubRegion> getService() {
    return subRegionService;
  }


}