package org.jhapy.backend.service.reference;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.jhapy.backend.domain.graphdb.reference.Country;
import org.jhapy.baseserver.service.CrudGraphdbService;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-27
 */
public interface CountryService extends CrudGraphdbService<Country> {

  Page<Country> findAnyMatching(String filter, Pageable pageable);

  long countAnyMatching(String filter);

  Country getById(Long id);

  Country getByIso2OrIso3(String iso2OrIso3Name);
}
