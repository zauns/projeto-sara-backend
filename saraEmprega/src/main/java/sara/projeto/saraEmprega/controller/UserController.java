package sara.projeto.saraEmprega.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.UserDTO;
import sara.projeto.saraEmprega.enums.UserAction;
import sara.projeto.saraEmprega.ports.UserServicePort;
import sara.projeto.saraEmprega.util.Mapper;
import sara.projeto.saraEmprega.util.user_concurrency.UserProcessor;
import sara.projeto.saraEmprega.util.user_concurrency.abstractions.UserOperationCreate;
import sara.projeto.saraEmprega.util.user_concurrency.abstractions.UserOperationUpdate;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(originPatterns = "http://localhost:3000")
public class UserController {

    UserProcessor processor;
    UserServicePort userService;

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id)
                .map(user -> new ContaResponseDTO(user))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO, Authentication auth){
        Jwt jwt = (Jwt) auth.getPrincipal();
        UserOperationUpdate operationUpdate = new UserOperationUpdate(UserAction.UPDATE_USER,jwt.getSubject(),userDTO);
        processor.addToQueue(operationUpdate);
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO, Authentication auth){
        Jwt jwt = (Jwt) auth.getPrincipal();
        UserOperationCreate operationCreate = new UserOperationCreate(UserAction.CREATE_USER,jwt.getSubject()
                ,Mapper.MapToUser(userDTO),jwt.getClaim("scope"));
        processor.addToQueue(operationCreate);
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(Authentication auth) {
        return ResponseEntity.ok("Token válido para: " + auth.getName());
    }

}
