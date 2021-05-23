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
import org.jhapy.backend.domain.graphdb.reference.Country;
import org.jhapy.backend.service.reference.CountryService;
import org.jhapy.baseserver.endpoint.BaseGraphDbEndpoint;
import org.jhapy.baseserver.service.CrudGraphdbService;
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
      BackendConverterV2 converter) {
    super(converter);
    this.countryService = countryService;
  }

  protected BackendConverterV2 getConverter() {
    return (BackendConverterV2) converter;
  }

  @Override
  protected CrudGraphdbService<Country> getService() {
    return countryService;
  }

  @Override
  protected org.jhapy.dto.domain.reference.Country convertToDto(Country domain,
      Map<String, Object> context) {
    return getConverter().convertToDto(domain, context);
  }

  @Override
  protected List<org.jhapy.dto.domain.reference.Country> convertToDtos(Iterable<Country> domains,
      Map<String, Object> context) {
    return getConverter().convertToDtoCountries(domains, context);
  }

  @Override
  protected Country convertToDomain(org.jhapy.dto.domain.reference.Country dto,
      Map<String, Object> context) {
    return getConverter().convertToDomain(dto, context);
  }

  @Override
  protected List<Country> convertToDomains(Iterable<org.jhapy.dto.domain.reference.Country> dto,
      Map<String, Object> context) {
    return getConverter().convertToDomainCountries(dto, context);
  }

  @PostMapping(value = "/getByIso2OrIso3")
  public ResponseEntity<ServiceResult> getByIso2OrIso3(@RequestBody GetByIso2OrIso3Query query) {
    var loggerPrefix = getLoggerPrefix("getByIso2OrIso3");

    return handleResult(loggerPrefix,
        getConverter().convertToDto(countryService.getByIso2OrIso3(query.getIso2OrIso3Name()),
            getContext(query)));
  }
}