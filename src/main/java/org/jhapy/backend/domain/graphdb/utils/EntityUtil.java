package org.jhapy.backend.domain.graphdb.utils;

import org.jhapy.baseserver.domain.graphdb.BaseEntity;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-26
 */
public final class EntityUtil {

  public static String getName(Class<? extends BaseEntity> type) {
    // All main domain have simple one word names, so this is sufficient. Metadata
    // could be added to the class if necessary.
    return type.getSimpleName();
  }
}
