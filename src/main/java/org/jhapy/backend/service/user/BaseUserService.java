package org.jhapy.backend.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.jhapy.backend.domain.graphdb.user.BaseUser;
import org.jhapy.baseserver.service.CrudGraphdbService;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-07
 */
public interface BaseUserService<T extends BaseUser> extends CrudGraphdbService<T> {

  Page<T> findAnyMatching(String filter, Boolean showInactive, Pageable pageable);

  long countAnyMatching(String filter, Boolean showInactive);

  T getBySecurityUserId(String securityUserId);

  T getById(Long id);
}
