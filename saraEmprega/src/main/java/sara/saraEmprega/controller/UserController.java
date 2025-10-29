package sara.projeto.saraEmprega.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sara.projeto.saraEmprega.dto.UserRequestDTO;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {   

    @PostMapping("/user")   //depois porta o createUser pra a classe contas controller, ela ta responsável por isso
    public ResponseEntity<UserRequestDTO> CreateUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        //falta o Response o user
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

}
