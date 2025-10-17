package sara.emprega.msusers.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sara.emprega.msusers.dto.UserRequestDTO;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {

@PostMapping("/user")
public ResponseEntity<UserRequestDTO> CreateUser(@RequestBody @Valid UserRequestDTO userRequestDTO){

}

}
