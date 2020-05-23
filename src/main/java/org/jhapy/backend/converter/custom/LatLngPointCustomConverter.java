package org.jhapy.backend.converter.custom;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;
import org.jhapy.dto.utils.LatLng;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-06-05
 */
@Component
public class LatLngPointCustomConverter extends
    BidirectionalConverter<LatLng, Point> {

  @Override
  public Point convertTo(LatLng source,
      Type<Point> destinationType, MappingContext context) {
    return new Point(source.getLat(), source.getLng());
  }

  @Override
  public LatLng convertFrom(Point source,
      Type<LatLng> destinationType, MappingContext context) {
    return new LatLng(source.getX(), source.getY());
  }
}
