package org.jhapy.backend.converter;

import com.google.maps.model.LatLng;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.jhapy.backend.domain.graphdb.utils.MapBounds;
import org.jhapy.commons.utils.OrikaBeanMapper;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-06-05
 */
@Component
public class UtilsConverter {

  private final OrikaBeanMapper orikaBeanMapper;

  public UtilsConverter(OrikaBeanMapper orikaBeanMapper) {
    this.orikaBeanMapper = orikaBeanMapper;
  }

  @Bean
  public void utilsConverters() {
    orikaBeanMapper.addMapper(LatLng.class, org.jhapy.dto.utils.LatLng.class);
    orikaBeanMapper.addMapper(org.jhapy.dto.utils.LatLng.class, LatLng.class);

    orikaBeanMapper.addMapper(MapBounds.class, org.jhapy.dto.utils.MapBounds.class);
    orikaBeanMapper.addMapper(org.jhapy.dto.utils.MapBounds.class, MapBounds.class);
  }
}