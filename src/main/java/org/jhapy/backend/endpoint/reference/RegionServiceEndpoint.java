package org.jhapy.backend.endpoint.reference;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.jhapy.backend.domain.graphdb.reference.Region;
import org.jhapy.backend.service.reference.RegionService;
import org.jhapy.commons.endpoint.BaseEndpoint;
import org.jhapy.commons.utils.OrikaBeanMapper;
import org.jhapy.dto.serviceQuery.ServiceResult;
import org.jhapy.dto.serviceQuery.generic.DeleteByIdQuery;
import org.jhapy.dto.serviceQuery.generic.GetByIdQuery;
import org.jhapy.dto.serviceQuery.generic.SaveQuery;
import org.jhapy.dto.serviceQuery.reference.region.CountAnyMatchingQuery;
import org.jhapy.dto.serviceQuery.reference.region.FindAnyMatchingQuery;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-06-05
 */
@RestController
@RequestMapping("/regionService")
public class RegionServiceEndpoint extends BaseEndpoint {

  private final RegionService regionService;

  public RegionServiceEndpoint(RegionService regionService,
      OrikaBeanMapper mapperFacade) {
    super(mapperFacade);
    this.regionService = regionService;
  }

  @PostMapping(value = "/findAnyMatching")
  public ResponseEntity<ServiceResult> findAnyMatching(@RequestBody FindAnyMatchingQuery query) {
    String loggerPrefix = getLoggerPrefix("findAnyMatching");
    try {
      Page<Region> result = regionService
          .findAnyMatching(query.getFilter(), query.getIso3Language(),
              mapperFacade.map(query.getPageable(),
                  Pageable.class, getOrikaContext(query)));
      return handleResult(loggerPrefix,
          mapperFacade
              .map(result, org.jhapy.dto.utils.Page.class, getOrikaContext(query)));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/countAnyMatching")
  public ResponseEntity<ServiceResult> countAnyMatching(@RequestBody CountAnyMatchingQuery query) {
    String loggerPrefix = getLoggerPrefix("countAnyMatching");
    try {
      return handleResult(loggerPrefix, regionService
          .countAnyMatching(query.getFilter(), query.getIso3Language()));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/getById")
  public ResponseEntity<ServiceResult> getById(@RequestBody GetByIdQuery query) {
    String loggerPrefix = getLoggerPrefix("getById");
    try {
      return handleResult(loggerPrefix, mapperFacade.map(regionService
              .load(query.getId()), org.jhapy.dto.domain.reference.Region.class,
          getOrikaContext(query)));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/save")
  public ResponseEntity<ServiceResult> save(
      @RequestBody SaveQuery<org.jhapy.dto.domain.reference.Region> query) {
    String loggerPrefix = getLoggerPrefix("save");
    try {
      return handleResult(loggerPrefix, mapperFacade.map(regionService
              .save(mapperFacade
                  .map(query.getEntity(), Region.class, getOrikaContext(query))),
          org.jhapy.dto.domain.reference.Region.class, getOrikaContext(query)));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/delete")
  public ResponseEntity<ServiceResult> delete(@RequestBody DeleteByIdQuery query) {
    String loggerPrefix = getLoggerPrefix("delete");
    try {
      regionService.delete(query.getId());
      return handleResult(loggerPrefix);
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }
}