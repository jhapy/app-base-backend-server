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

package org.jhapy.backend.domain.graphdb.user;

import org.jhapy.baseserver.domain.graphdb.BaseEntityNoRelations;

import java.util.UUID;

/**
 * This class represent a User.
 *
 * <p>A User can be : - System user (internal) - Admin user (to administer the platform) - Player or
 * Fan - Owner of a Place
 *
 * @author jHapy Lead Dev
 * @version 1.0
 * @since 2019-03-06
 */
public interface BaseUserNoRelations extends BaseEntityNoRelations {

  String getSecurityUserId();

  String getEmail();

  String getFirstName();

  String getLastName();

  String getFullName();

  String getNickName();

  UUID getAvatarId();
}