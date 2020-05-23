package org.jhapy.backend.endpoint.reference;

import java.util.List;
import ma.glasnost.orika.MappingContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.jhapy.backend.domain.graphdb.reference.IntermediateRegion;
import org.jhapy.backend.service.reference.IntermediateRegionService;
import org.jhapy.commons.endpoint.BaseEndpoint;
import org.jhapy.commons.utils.OrikaBeanMapper;
import org.jhapy.dto.serviceQuery.BaseRemoteQuery;
import org.jhapy.dto.serviceQuery.ServiceResult;
import org.jhapy.dto.serviceQuery.generic.DeleteByIdQuery;
import org.jhapy.dto.serviceQuery.generic.GetByIdQuery;
import org.jhapy.dto.serviceQuery.generic.SaveQuery;
import org.jhapy.dto.serviceQuery.reference.intermediateRegion.CountAnyMatchingQuery;
import org.jhapy.dto.serviceQuery.reference.intermediateRegion.FindAnyMatchingQuery;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-06-05
 */
@RestController
@RequestMapping("/intermediateRegionService")
public class IntermediateRegionServiceEndpoint extends BaseEndpoint {

  private final IntermediateRegionService intermediateRegionService;

  public IntermediateRegionServiceEndpoint(IntermediateRegionService intermediateRegionService,
      OrikaBeanMapper mapperFacade) {
    super(mapperFacade);
    this.intermediateRegionService = intermediateRegionService;
  }

  @PostMapping(value = "/findAll")
  public ResponseEntity<ServiceResult> findAll(BaseRemoteQuery query) {
    String loggerPrefix = getLoggerPrefix("findAll");
    try {
      List<IntermediateRegion> result = intermediateRegionService
          .findAll();
      return handleResult(loggerPrefix,
          mapperFacade
              .mapAsList(result, org.jhapy.dto.domain.reference.IntermediateRegion.class,
                  getOrikaContext(query)));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/findAnyMatching")
  public ResponseEntity<ServiceResult> findAnyMatching(@RequestBody FindAnyMatchingQuery query) {
    String loggerPrefix = getLoggerPrefix("findAnyMatching");
    try {
      Page<IntermediateRegion> result = intermediateRegionService
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
      return handleResult(loggerPrefix, intermediateRegionService
          .countAnyMatching(query.getFilter(), query.getIso3Language()));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/getById")
  public ResponseEntity<ServiceResult> getById(@RequestBody GetByIdQuery query) {
    String loggerPrefix = getLoggerPrefix("getById");
    try {
      MappingContext context = new MappingContext.Factory().getContext();
      context.setProperty("iso3Language", query.get_iso3Language());

      return handleResult(loggerPrefix, mapperFacade.map(intermediateRegionService
              .load(query.getId()), org.jhapy.dto.domain.reference.IntermediateRegion.class,
          getOrikaContext(query)));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/save")
  public ResponseEntity<ServiceResult> save(
      @RequestBody SaveQuery<org.jhapy.dto.domain.reference.IntermediateRegion> query) {
    String loggerPrefix = getLoggerPrefix("save");
    try {
      MappingContext context = new MappingContext.Factory().getContext();
      context.setProperty("iso3Language", query.get_iso3Language());

      return handleResult(loggerPrefix, mapperFacade.map(intermediateRegionService
              .save(mapperFacade
                  .map(query.getEntity(), IntermediateRegion.class, getOrikaContext(query))),
          org.jhapy.dto.domain.reference.IntermediateRegion.class,
          getOrikaContext(query)));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/delete")
  public ResponseEntity<ServiceResult> delete(@RequestBody DeleteByIdQuery query) {
    String loggerPrefix = getLoggerPrefix("delete");
    try {
      intermediateRegionService
          .delete(query.getId());
      return handleResult(loggerPrefix);
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }
}