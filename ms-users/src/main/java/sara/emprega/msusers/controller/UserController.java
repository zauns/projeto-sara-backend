package sara.emprega.msusers.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sara.emprega.msusers.dto.UserDTO;
import sara.emprega.msusers.enums.UserAction;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.ports.UserServicePort;
import sara.emprega.msusers.util.Mapper;
import sara.emprega.msusers.util.jwt.UserAuthenticated;
import sara.emprega.msusers.util.user_concurrency.UserProcessor;
import sara.emprega.msusers.util.user_concurrency.abstractions.UserOperationCreate;
import sara.emprega.msusers.util.user_concurrency.abstractions.UserOperationUpdate;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    UserProcessor processor;

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
