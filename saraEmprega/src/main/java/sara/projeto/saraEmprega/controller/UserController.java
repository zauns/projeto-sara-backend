package sara.projeto.saraEmprega.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sara.projeto.saraEmprega.dto.UserDTO;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.UserServicePort;
import sara.projeto.saraEmprega.util.Mapper;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private final UserServicePort userService;

	@PostMapping("/update")
	public ResponseEntity<UserDTO> updateUser(
		@RequestBody @Valid UserDTO dto,
		Authentication auth
	) {
		Jwt jwt = (Jwt) auth.getPrincipal();
		User user = userService.updateUser(dto, jwt.getSubject());
		UserDTO updatedUser = Mapper.mapToUserRequestDTO(user);
		return ResponseEntity.ok(updatedUser);
	}

	@PostMapping("/create")
	public ResponseEntity<UserDTO> createUser(
		@RequestBody @Valid UserDTO userDTO,
		Authentication auth
	) {
		//Jwt jwt = (Jwt) auth.getPrincipal();
		User user = Mapper.MapToUser(userDTO);
		User createdUser = userService.createUser(user);
		UserDTO userResponseDTO = Mapper.mapToUserRequestDTO(createdUser);
		return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
	}

	@GetMapping("/validate-token")
	public ResponseEntity<String> validateToken(Authentication auth) {
		return ResponseEntity.ok("Token válido para: " + auth.getName());
	}
}
