package sara.projeto.saraEmprega.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import sara.projeto.saraEmprega.dto.ContaResponseDTO;
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

	@PutMapping("/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<UserDTO> updateUser(
		@RequestBody @Valid UserDTO dto,
		Authentication auth
	) {
		Jwt jwt = (Jwt) auth.getPrincipal();
		UUID userId = UUID.fromString(jwt.getClaimAsString("userId"));
		User user = userService.updateUser(dto, userId);
		UserDTO updatedUser = Mapper.mapToUserRequestDTO(user);
		return ResponseEntity.ok(updatedUser);
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('SECRETARIA') or hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
	public ResponseEntity<UserDTO> createUser(
		@RequestBody @Valid UserDTO userDTO
	) {
		User user = Mapper.MapToUser(userDTO);
		User createdUser = userService.createUser(user);
		UserDTO userResponseDTO = Mapper.mapToUserRequestDTO(createdUser);
		return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
	}

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ContaResponseDTO> getMe(Authentication auth) {
		Jwt jwt = (Jwt) auth.getPrincipal();
		UUID userId = UUID.fromString(jwt.getClaimAsString("userId"));
		
		User user = userService.findById(userId);
		return ResponseEntity.ok(new ContaResponseDTO(user));
	}

	@DeleteMapping("/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Void> deleteMe(Authentication auth) {
		Jwt jwt = (Jwt) auth.getPrincipal();
		UUID userId = UUID.fromString(jwt.getClaimAsString("userId"));

		userService.deleteUserById(userId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/validate-token")
	public ResponseEntity<String> validateToken(Authentication auth) {
		return ResponseEntity.ok("Token válido para: " + auth.getName());
	}
}
