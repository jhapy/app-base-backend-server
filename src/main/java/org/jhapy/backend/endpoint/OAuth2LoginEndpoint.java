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

package org.jhapy.backend.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-05-17
 */
@Controller
public class OAuth2LoginEndpoint {

  @GetMapping("/facebookLogin")
  public void facebookLogin(String access_token) {
  }

  @GetMapping("/googleLogin")
  public void googleLogin(String access_token) {
  }

  @GetMapping("/twitterLogin")
  public void twitterLogin(String access_token) {
  }
}
