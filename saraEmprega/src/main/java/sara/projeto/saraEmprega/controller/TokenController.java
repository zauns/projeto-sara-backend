package sara.projeto.saraEmprega.controller;

import java.time.Instant;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sara.projeto.saraEmprega.dto.LoginRequestDTO;
import sara.projeto.saraEmprega.ports.TokenServicesPort;
import sara.projeto.saraEmprega.service.TokenServices;

@AllArgsConstructor
@RestController
@RequestMapping("/token")
public class TokenController {

	private final TokenServicesPort tokenServices;
	private final AuthenticationManager authenticationManager;

	@PostMapping
	public ResponseEntity<String> token(
		@RequestBody LoginRequestDTO loginRequest
	) {
		var authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				loginRequest.username(),
				loginRequest.password()
			)
		);
		String jwt = tokenServices.token(authentication);
		return ResponseEntity.ok().body(jwt);
	}
}
