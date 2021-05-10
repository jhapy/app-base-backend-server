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

import org.jhapy.backend.domain.graphdb.reference.Country;
import org.jhapy.backend.service.reference.CountryService;
import org.jhapy.baseserver.endpoint.BaseGraphDbEndpoint;
import org.jhapy.baseserver.service.CrudGraphdbService;
import org.jhapy.commons.utils.OrikaBeanMapper;
import org.jhapy.dto.serviceQuery.ServiceResult;
import org.jhapy.dto.serviceQuery.reference.country.GetByIso2OrIso3Query;
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
@RequestMapping("/api/countryService")
public class CountryServiceEndpoint extends
    BaseGraphDbEndpoint<Country, org.jhapy.dto.domain.reference.Country> {

  private final CountryService countryService;

  public CountryServiceEndpoint(CountryService countryService,
      OrikaBeanMapper mapperFacade) {
    super(mapperFacade);
    this.countryService = countryService;
  }

  @Override
  protected CrudGraphdbService<Country> getService() {
    return countryService;
  }

  @Override
  protected Class<Country> getEntityClass() {
    return Country.class;
  }

  @Override
  protected Class<org.jhapy.dto.domain.reference.Country> getDtoClass() {
    return org.jhapy.dto.domain.reference.Country.class;
  }

  @PostMapping(value = "/getByIso2OrIso3")
  public ResponseEntity<ServiceResult> getByIso2OrIso3(@RequestBody GetByIso2OrIso3Query query) {
    var loggerPrefix = getLoggerPrefix("getByIso2OrIso3");

      return handleResult(loggerPrefix,
          mapperFacade.map(countryService.getByIso2OrIso3(query.getIso2OrIso3Name()),
              org.jhapy.dto.domain.reference.Country.class, getOrikaContext(query)));
  }
}