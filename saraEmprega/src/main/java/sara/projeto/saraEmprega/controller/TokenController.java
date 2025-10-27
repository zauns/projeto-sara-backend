package sara.projeto.saraEmprega.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import sara.projeto.saraEmprega.ports.TokenServicesPort;

@AllArgsConstructor
@RestController
@RequestMapping("/token")
public class TokenController {

    private final TokenServicesPort tokenServices;

    @PostMapping()
    public ResponseEntity<String> token(Authentication authentication) {
      return ResponseEntity.ok().body(tokenServices.token(authentication));
    }

}
