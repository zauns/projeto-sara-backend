package sara.projeto.saraEmprega.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sara.projeto.saraEmprega.dto.UserDTO;
import sara.projeto.saraEmprega.dto.UserRequestDTO;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.UserServicePort;
import sara.projeto.saraEmprega.util.Mapper;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	UserServicePort userService;

	@PostMapping("/update")
	public ResponseEntity<UserDTO> updateUser(
		@RequestBody @Valid UserDTO userDTO,
		Authentication auth
	) {
		Jwt jwt = (Jwt) auth.getPrincipal();
		User user = userService.updateUser(userDTO, jwt.getSubject());
		//UserDTO userResponseDTO = Mapper.mapToUserRequestDTO(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<UserDTO> createUser(
		@RequestBody @Valid UserDTO userDTO,
		Authentication auth
	) {
		Jwt jwt = (Jwt) auth.getPrincipal();
		User user = userService.CreateUser(
			jwt.getClaim("scope"),
			Mapper.MapToUser(userDTO)
		);
		UserDTO userResponseDTO = Mapper.mapToUserRequestDTO(user);
		return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
	}

	@GetMapping("/validate-token")
	public ResponseEntity<String> validateToken(Authentication auth) {
		return ResponseEntity.ok("Token válido para: " + auth.getName());
	}
}
