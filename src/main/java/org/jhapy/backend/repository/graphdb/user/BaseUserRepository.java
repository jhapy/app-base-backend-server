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

package org.jhapy.backend.repository.graphdb.user;

import java.util.List;
import org.jhapy.backend.domain.graphdb.user.BaseUser;
import org.jhapy.baseserver.repository.graphdb.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-06
 */
public interface BaseUserRepository<T extends BaseUser> extends BaseRepository<T> {

  T findByEmailIgnoreCase(String email);

  @Depth(0)
  T getBySecurityUserId(String securityUserId);

  @Depth(0)
  List<T> getByEmail(String email);

  @Query(value = "MATCH (u:BaseUser) WHERE u.email =~ {filter} OR u.firstName =~ {filter} OR u.lastName =~ {filter} RETURN u, c",
      countQuery = "MATCH (u:BaseUser) WHERE u.email =~ {filter} OR u.firstName =~ {filter} OR u.lastName =~ {filter} RETURN count(u)")
  Page<T> findAnyMatching(String filter, Pageable pageable);

  @Query(value = "MATCH (u:BaseUser) WHERE u.email =~ {filter} OR u.firstName =~ {filter} OR u.lastName =~ {filter} RETURN count(u)")
  long countAnyMatching(String filter);
}
