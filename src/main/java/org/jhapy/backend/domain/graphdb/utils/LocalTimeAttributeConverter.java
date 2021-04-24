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

package org.jhapy.backend.domain.graphdb.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-05-25
 */
public class LocalTimeAttributeConverter implements
    Neo4jPersistentPropertyConverter<LocalTime> {

    @Override
    public Value write(LocalTime localTime) {
        if (localTime != null) {
            return Values.value(localTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        } else {
            return null;
        }
    }

    @Override
    public LocalTime read(Value source) {
        if (StringUtils.isNotBlank(source.asString())) {
            return LocalTime.parse(source.asString(), DateTimeFormatter.ofPattern("HH:mm"));
        } else {
            return null;
        }
    }
}
