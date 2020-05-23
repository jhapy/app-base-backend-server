package org.jhapy.backend.domain.graphdb.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jhapy.baseserver.domain.graphdb.BaseEntity;
import org.jhapy.dto.utils.StoredFile;

/**
 * This class represent a User.
 *
 * A User can be : - System user (internal) - Admin user (to administer the platform) - Player or
 * Fan - Owner of a Place
 *
 * @author jHapy Lead Dev
 * @version 1.0
 * @since 2019-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseUser extends BaseEntity {

  private String securityUserId;

  private String email;

  private String firstName;

  private String lastName;

  private String fullName;

  private String nickName;

  @org.springframework.data.annotation.Transient
  @org.neo4j.ogm.annotation.Transient
  private StoredFile avatar = null;

  private String avatarId = null;
}
