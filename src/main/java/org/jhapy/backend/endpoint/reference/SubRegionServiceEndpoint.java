package org.jhapy.backend.endpoint.reference;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.jhapy.backend.domain.graphdb.reference.SubRegion;
import org.jhapy.backend.service.reference.SubRegionService;
import org.jhapy.commons.endpoint.BaseEndpoint;
import org.jhapy.commons.utils.OrikaBeanMapper;
import org.jhapy.dto.serviceQuery.ServiceResult;
import org.jhapy.dto.serviceQuery.generic.DeleteByIdQuery;
import org.jhapy.dto.serviceQuery.generic.GetByIdQuery;
import org.jhapy.dto.serviceQuery.generic.SaveQuery;
import org.jhapy.dto.serviceQuery.reference.subRegion.CountAnyMatchingQuery;
import org.jhapy.dto.serviceQuery.reference.subRegion.FindAnyMatchingQuery;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-06-05
 */
@RestController
@RequestMapping("/subSubRegionService")
public class SubRegionServiceEndpoint extends BaseEndpoint {

  private final SubRegionService subSubRegionService;

  public SubRegionServiceEndpoint(SubRegionService subSubRegionService,
      OrikaBeanMapper mapperFacade) {
    super(mapperFacade);
    this.subSubRegionService = subSubRegionService;
  }

  @PostMapping(value = "/findAnyMatching")
  public ResponseEntity<ServiceResult> findAnyMatching(@RequestBody FindAnyMatchingQuery query) {
    String loggerPrefix = getLoggerPrefix("findAnyMatching");
    try {
      Page<SubRegion> result = subSubRegionService
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
      return handleResult(loggerPrefix, subSubRegionService
          .countAnyMatching(query.getFilter(), query.getIso3Language()));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/getById")
  public ResponseEntity<ServiceResult> getById(@RequestBody GetByIdQuery query) {
    String loggerPrefix = getLoggerPrefix("getById");
    try {
      return handleResult(loggerPrefix, mapperFacade.map(subSubRegionService
              .load(query.getId()), org.jhapy.dto.domain.reference.SubRegion.class,
          getOrikaContext(query)));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/save")
  public ResponseEntity<ServiceResult> save(
      @RequestBody SaveQuery<org.jhapy.dto.domain.reference.SubRegion> query) {
    String loggerPrefix = getLoggerPrefix("save");
    try {
      return handleResult(loggerPrefix, mapperFacade.map(subSubRegionService
              .save(mapperFacade
                  .map(query.getEntity(), SubRegion.class, getOrikaContext(query))),
          org.jhapy.dto.domain.reference.SubRegion.class, getOrikaContext(query)));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/delete")
  public ResponseEntity<ServiceResult> delete(@RequestBody DeleteByIdQuery query) {
    String loggerPrefix = getLoggerPrefix("delete");
    try {
      subSubRegionService.delete(query.getId());
      return handleResult(loggerPrefix);
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }
}