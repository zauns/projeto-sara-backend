package sara.emprega.msusers.ports;

import sara.emprega.msusers.dto.UserDTO;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.util.jwt.UserAuthenticated;

import java.util.UUID;


public interface UserServicePort {

    // --- Consultas ---
    User getUserByMail(String mail);
    User getUserById(UUID id);

    User updateUser(UserDTO userDTO, String mail);
    User curriculumUpdate(User user);
    User CreateUser(String claim, User user);

    /*
    void updateUserRoles(UUID id, List<String> roles);

    // --- Exclusão ---
    void deleteUserById(UUID id);
    void deleteUserByMail(String mail);

    // --- Verificação / Existência ---
    boolean existsByMail(String mail);
    boolean existsById(UUID id);
*/
}

