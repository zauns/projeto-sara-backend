package sara.projeto.saraEmprega.ports;

import sara.projeto.saraEmprega.dto.UserDTO;
import sara.projeto.saraEmprega.model.User;

import java.util.UUID;


public interface UserServicePort {
    // --- Consultas ---
    User findByEmail(String email);
    User findById(UUID id);
    User updateUser(UserDTO userDTO, UUID id);
    User createUser(User user);
    User updateCurriculum(User user);
    boolean existsByEmail(String email);
    void deleteUserById(UUID id);
    /*
    void updateUserRoles(UUID id, List<String> roles);

    // --- Exclusão ---
    void deleteUserByMail(String mail);

    // --- Verificação / Existência ---
    boolean existsByMail(String mail);
    boolean existsById(UUID id);
*/
}
