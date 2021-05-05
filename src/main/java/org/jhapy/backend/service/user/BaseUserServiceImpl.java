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

package org.jhapy.backend.service.user;

import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.jhapy.backend.domain.graphdb.user.BaseUser;
import org.jhapy.backend.repository.graphdb.user.BaseUserRepository;
import org.jhapy.baseserver.client.ResourceService;
import org.jhapy.dto.domain.exception.UserFriendlyDataException;
import org.jhapy.dto.serviceQuery.generic.DeleteByStrIdQuery;
import org.jhapy.dto.serviceQuery.generic.SaveQuery;
import org.jhapy.dto.utils.AppContextThread;
import org.jhapy.dto.utils.StoredFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-06
 */
@Service
@Transactional(readOnly = true)
public class BaseUserServiceImpl<T extends BaseUser> implements BaseUserService<T> {

  public static final String MODIFY_LOCKED_USER_NOT_PERMITTED = "User has been locked and cannot be modified or deleted";
  private static final String DELETING_SELF_NOT_PERMITTED = "You cannot delete your own account";

  private final BaseUserRepository<T> userRepository;
  private final ResourceService resourceService;

  public BaseUserServiceImpl(BaseUserRepository userRepository,
      ResourceService resourceService) {
    this.userRepository = userRepository;
    this.resourceService = resourceService;
  }

  @Override
  public T getBySecurityUserId(String securityUserId) {
    return userRepository.getBySecurityUserId(securityUserId);
  }

  @Override
  public List<T> getByEmail(String email) {
    return userRepository.getByEmail(email);
  }

  @Override
  public T getById(Long id) {
    return userRepository.findById(id).get();
  }

  @Override
  public BaseUserRepository<T> getRepository() {
    return userRepository;
  }

  public Page<T> find(Pageable pageable) {
    return getRepository().findAll(pageable);
  }

  @Override
  public Page<T> findAnyMatching(String filter, Boolean showInactive, Pageable pageable) {
    if (StringUtils.isNotBlank(filter)) {
      return userRepository
          .findAnyMatching(filter, pageable);
    } else {
      return userRepository.findAll(pageable);
    }
  }

  @Override
  public long countAnyMatching(String filter, Boolean showInactive) {
    if (StringUtils.isNotBlank(filter)) {
      return userRepository
          .countAnyMatching(filter);
    } else {
      return userRepository.count();
    }
  }

  @Override
  @Transactional
  public T save(T entity) {
    if (!entity.getIsPreviouslyLoaded()) {
      Optional<T> _previousValue = Optional.empty();
      if (entity.getId() != null) {
        _previousValue = userRepository.findById(entity.getId());
      }
      T previousValue = _previousValue.orElse(null);
      if (previousValue != null) {
        if (previousValue.getAvatarId() != null && entity.getAvatarId() == null) {
          resourceService.delete(new DeleteByStrIdQuery(previousValue.getAvatarId()));
        }
      }
    } else {
      if (entity.getPreviousAvatarId() != null && entity.getAvatarId() == null) {
        // Image change, remove old one
        resourceService.delete(new DeleteByStrIdQuery(entity.getPreviousAvatarId()));
      }
    }

    if (entity.getAvatar() != null && entity.getAvatar().getContent() != null) {
      StoredFile newImage = resourceService.save(new SaveQuery<>(entity.getAvatar())).getData();
      entity.setAvatar(newImage);
      entity.setAvatarId(newImage.getId());
    }

    String fullName = "";
    if (StringUtils.isNotBlank(entity.getFirstName())) {
      fullName += entity.getFirstName();
    }
    fullName += " ";
    if (StringUtils.isNotBlank(entity.getLastName())) {
      fullName += entity.getLastName();
    }
    entity.setFullName(fullName.trim());

    return userRepository.save(entity); // , 0);
  }

  @Override
  @Transactional
  public void delete(T entity) {
    throwIfDeletingSelf(entity);

    if (entity.getAvatar() != null) {
      resourceService.delete(new DeleteByStrIdQuery(entity.getAvatar().getId()));
    }

    getRepository().delete(entity);
  }

  private void throwIfDeletingSelf(T user) {
    if (AppContextThread.getCurrentUserId().equals(user.getId().toString())) {
      throw new UserFriendlyDataException(DELETING_SELF_NOT_PERMITTED);
    }
  }
}
