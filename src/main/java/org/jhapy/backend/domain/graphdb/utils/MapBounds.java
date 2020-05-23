package org.jhapy.backend.domain.graphdb.utils;

import com.google.maps.model.LatLng;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapBounds implements Serializable {

  private LatLng northEast;
  private LatLng southWest;
}
