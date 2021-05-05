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

import org.jhapy.backend.domain.graphdb.reference.SubRegion;
import org.jhapy.backend.service.reference.SubRegionService;
import org.jhapy.baseserver.endpoint.BaseGraphDbEndpoint;
import org.jhapy.baseserver.service.CrudGraphdbService;
import org.jhapy.commons.utils.OrikaBeanMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-06-05
 */
@RestController
@RequestMapping("/api/subSubRegionService")
public class SubRegionServiceEndpoint extends
    BaseGraphDbEndpoint<SubRegion, org.jhapy.dto.domain.reference.SubRegion> {

  private final SubRegionService subSubRegionService;

  public SubRegionServiceEndpoint(SubRegionService subSubRegionService,
      OrikaBeanMapper mapperFacade) {
    super(mapperFacade);
    this.subSubRegionService = subSubRegionService;
  }

  @Override
  protected CrudGraphdbService<SubRegion> getService() {
    return subSubRegionService;
  }

  @Override
  protected Class<SubRegion> getEntityClass() {
    return SubRegion.class;
  }

  @Override
  protected Class<org.jhapy.dto.domain.reference.SubRegion> getDtoClass() {
    return org.jhapy.dto.domain.reference.SubRegion.class;
  }
}