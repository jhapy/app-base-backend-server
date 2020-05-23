package org.jhapy.backend.service.reference;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.jhapy.backend.domain.graphdb.reference.IntermediateRegion;
import org.jhapy.baseserver.service.CrudGraphdbService;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-27
 */
public interface IntermediateRegionService extends CrudGraphdbService<IntermediateRegion> {

  List<IntermediateRegion> findAll();

  Page<IntermediateRegion> findAnyMatching(String filter, String iso3Language, Pageable pageable);

  long countAnyMatching(String filter, String iso3Language);

  IntermediateRegion getById(Long id);
}
