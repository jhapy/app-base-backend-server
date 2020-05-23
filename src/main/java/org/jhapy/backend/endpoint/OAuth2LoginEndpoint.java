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
