package sara.projeto.saraEmprega.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.UserRequestDTO;
import sara.projeto.saraEmprega.ports.UserServicePort;

@Validated
@RestController
@RequestMapping("/api/user")
@CrossOrigin(originPatterns = "http://localhost:3000")
public class UserController extends ContasController<UserRequestDTO, UserServicePort> {

    // private final UserProcessor processor;
    private final UserServicePort userService;

    protected UserController(UserServicePort service) {
        super(service);
        // this.processor = processor;
		this.userService = service;
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('SECRETARIA')")
    public ResponseEntity<ContaResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userDTO) {
        ContaResponseDTO novoUsuario = userService.create(userDTO);
        return new ResponseEntity<>(novoUsuario, HttpStatus.OK);
    }

    @GetMapping("/dados/{id}")
    @PreAuthorize("hasRole('SECRETARIA') or hasRole('USER')")
    public ResponseEntity<UserRequestDTO> getDados(@PathVariable UUID id){
        UserRequestDTO user = service.getDados(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') and authentication.principal.claims['userId'] == #id.toString()")
    public ResponseEntity<ContaResponseDTO> updateUser(@PathVariable UUID id, @RequestBody @Valid UserRequestDTO userDTO) {
        ContaResponseDTO updatedUser = userService.update(id, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(Authentication auth) {
        return ResponseEntity.ok("Token válido para: " + auth.getName());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') and authentication.principal.claims['userId'] == #id.toString()")
    public ResponseEntity<Void> excluirConta(@PathVariable UUID id){
        service.excluirConta(id);
        return ResponseEntity.noContent().build();
    }
}
