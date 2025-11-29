package sara.projeto.saraEmprega.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sara.projeto.saraEmprega.dto.LoginRequestDTO;
import sara.projeto.saraEmprega.ports.TokenServicesPort;

@AllArgsConstructor
@RestController
@RequestMapping("/token")
@CrossOrigin(origins = "http://localhost:3000") // libera o acesso com o front-end
public class TokenController {

	private final TokenServicesPort tokenServices;
	private final AuthenticationManager authenticationManager;

	@PostMapping
	public ResponseEntity<String> token(@RequestBody @Valid LoginRequestDTO loginRequest) {
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
