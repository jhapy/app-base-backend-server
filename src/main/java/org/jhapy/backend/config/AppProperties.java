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

package org.jhapy.backend.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "jhapy")
public class AppProperties extends org.jhapy.commons.config.AppProperties {

  private final Bootstrap bootstrap = new Bootstrap();

  @Data
  public static final class Bootstrap {

    private Iso3166 iso3166 = new Iso3166();
    private CategoryList categoryList = new CategoryList();

    @Data
    public static final class Iso3166 {

      private boolean enabled;
      private String file;
    }

    @Data
    public static final class CategoryList {

      private boolean enabled;
      private String file;
    }
  }
}
