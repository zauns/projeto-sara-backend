package sara.projeto.saraEmprega.ports;

import sara.projeto.saraEmprega.dto.UserDTO;
import sara.projeto.saraEmprega.model.User;

import java.util.Optional;
import java.util.UUID;


public interface UserServicePort {
    // --- Consultas ---
    Optional<User> getUserById(UUID id);
    User getUserByMail(String mail);
    User updateUser(UserDTO userDTO, String email);
    User createUser(String claim, User user);
    User curriculumUpdate(User user);
    // User updateCurriculum(User user);
    // boolean existsByEmail(String email);
    // void deleteUserById(UUID id);
    /*
    void updateUserRoles(UUID id, List<String> roles);

    // --- Exclusão ---
    void deleteUserByMail(String mail);

    // --- Verificação / Existência ---
    boolean existsByMail(String mail);
    boolean existsById(UUID id);
*/
}
