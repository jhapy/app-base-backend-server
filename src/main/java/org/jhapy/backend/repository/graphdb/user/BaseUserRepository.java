package org.jhapy.backend.repository.graphdb.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.jhapy.backend.domain.graphdb.user.BaseUser;
import org.jhapy.baseserver.repository.graphdb.BaseRepository;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-06
 */
public interface BaseUserRepository<T extends BaseUser> extends BaseRepository<T> {

  T findByEmailIgnoreCase(String email);

  @Depth(0)
  T getBySecurityUserId(String securityUserId);

  @Query(value = "MATCH (u:BaseUser) WHERE u.email =~ {filter} OR u.firstName =~ {filter} OR u.lastName =~ {filter} RETURN u, c",
      countQuery = "MATCH (u:BaseUser) WHERE u.email =~ {filter} OR u.firstName =~ {filter} OR u.lastName =~ {filter} RETURN count(u)")
  Page<T> findAnyMatching(String filter, Pageable pageable);

  @Query(value = "MATCH (u:BaseUser) WHERE u.email =~ {filter} OR u.firstName =~ {filter} OR u.lastName =~ {filter} RETURN count(u)")
  long countAnyMatching(String filter);
}
