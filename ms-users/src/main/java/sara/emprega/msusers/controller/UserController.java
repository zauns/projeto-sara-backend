package sara.emprega.msusers.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sara.emprega.msusers.dto.UserDTO;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.ports.UserServicePort;
import sara.emprega.msusers.util.Mapper;
import sara.emprega.msusers.util.jwt.UserAuthenticated;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    UserServicePort userService;

    @PostMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO
                                                , Authentication auth){
        UserAuthenticated userAuth = (UserAuthenticated) auth.getPrincipal();
        User user = userService.updateUser(userDTO,userAuth);
        UserDTO userResponseDTO = Mapper.mapToUserRequestDTO(user);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

}
