package org.jhapy.backend.domain.graphdb.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.typeconversion.AttributeConverter;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-05-25
 */
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, String> {

  @Override
  public String toGraphProperty(LocalTime localTime) {
    if (localTime != null) {
      return localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    } else {
      return null;
    }
  }

  @Override
  public LocalTime toEntityAttribute(String s) {
    if (StringUtils.isNotBlank(s)) {
      return LocalTime.parse(s, DateTimeFormatter.ofPattern("HH:mm"));
    } else {
      return null;
    }
  }
}
