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
import ma.glasnost.orika.MappingContext;
import org.jhapy.backend.domain.graphdb.reference.IntermediateRegion;
import org.jhapy.backend.service.reference.IntermediateRegionService;
import org.jhapy.baseserver.endpoint.BaseGraphDbEndpoint;
import org.jhapy.baseserver.service.CrudGraphdbService;
import org.jhapy.commons.endpoint.BaseEndpoint;
import org.jhapy.commons.utils.OrikaBeanMapper;
import org.jhapy.dto.serviceQuery.BaseRemoteQuery;
import org.jhapy.dto.serviceQuery.ServiceResult;
import org.jhapy.dto.serviceQuery.generic.DeleteByIdQuery;
import org.jhapy.dto.serviceQuery.generic.GetByIdQuery;
import org.jhapy.dto.serviceQuery.generic.SaveQuery;
import org.jhapy.dto.serviceQuery.reference.intermediateRegion.CountAnyMatchingQuery;
import org.jhapy.dto.serviceQuery.reference.intermediateRegion.FindAnyMatchingQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-06-05
 */
@RestController
@RequestMapping("/api/intermediateRegionService")
public class IntermediateRegionServiceEndpoint extends
    BaseGraphDbEndpoint<IntermediateRegion, org.jhapy.dto.domain.reference.IntermediateRegion> {

  private final IntermediateRegionService intermediateRegionService;

  public IntermediateRegionServiceEndpoint(IntermediateRegionService intermediateRegionService,
      OrikaBeanMapper mapperFacade) {
    super(mapperFacade);
    this.intermediateRegionService = intermediateRegionService;
  }

  @Override
  protected CrudGraphdbService<IntermediateRegion> getService() {
    return intermediateRegionService;
  }

  @Override
  protected Class<IntermediateRegion> getEntityClass() {
    return IntermediateRegion.class;
  }

  @Override
  protected Class<org.jhapy.dto.domain.reference.IntermediateRegion> getDtoClass() {
    return org.jhapy.dto.domain.reference.IntermediateRegion.class;
  }
}