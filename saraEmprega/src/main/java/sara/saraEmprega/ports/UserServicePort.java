package sara.projeto.saraEmprega.ports;

import sara.projeto.saraEmprega.dto.UserDTO;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.util.jwt.UserAuthenticated;

import java.util.UUID;


public interface UserServicePort {
    // --- Consultas ---
    User getUserByMail(String mail);
    User getUserById(UUID id);

    User updateUser(UserDTO userDTO, UserAuthenticated userAuth);
    User curriculumUpdate(User user);

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
