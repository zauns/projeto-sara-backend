package sara.projeto.saraEmprega.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import sara.projeto.saraEmprega.ports.TokenServicesPort;
import sara.projeto.saraEmprega.service.TokenServices;

import java.time.Instant;
import java.util.stream.Collectors;

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
