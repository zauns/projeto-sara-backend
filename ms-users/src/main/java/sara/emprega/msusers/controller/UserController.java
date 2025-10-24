package sara.emprega.msusers.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sara.emprega.msusers.dto.UserRequestDTO;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {

@PostMapping("/update")
public ResponseEntity<UserRequestDTO> updateUser(@RequestBody @Valid UserRequestDTO userRequestDTO
                                                ,Authentication auth){


}

}
