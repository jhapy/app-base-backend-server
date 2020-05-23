package org.jhapy.backend.converter;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.jhapy.backend.domain.graphdb.user.BaseUser;
import org.jhapy.commons.utils.OrikaBeanMapper;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-06-05
 */
@Component
public class BaseUserConverter {

  private final OrikaBeanMapper orikaBeanMapper;

  public BaseUserConverter(OrikaBeanMapper orikaBeanMapper) {
    this.orikaBeanMapper = orikaBeanMapper;
  }

  @Bean
  public void utilsConverters() {
    orikaBeanMapper.addMapper(BaseUser.class, org.jhapy.dto.domain.user.BaseUser.class);
    orikaBeanMapper.addMapper(org.jhapy.dto.domain.user.BaseUser.class, BaseUser.class);
  }
}