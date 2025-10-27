package sara.projeto.saraEmprega.ports;

import java.util.List;
import java.util.UUID;
import sara.projeto.saraEmprega.dto.UserDTO;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.util.jwt.UserAuthenticated;

public interface UserServicePort {
    // --- Consultas ---
    User getUserByMail(String mail);
    User getUserById(UUID id);
    List<User> getAllUsers();
    List<User> getUsersByRole(String role);

    User updateUser(UserDTO userDTO, UserAuthenticated userAuth);

    void updateUserRoles(UUID id, List<String> roles);

    // --- Exclusão ---
    void deleteUserById(UUID id);
    void deleteUserByMail(String mail);

    // --- Verificação / Existência ---
    boolean existsByMail(String mail);
    boolean existsById(UUID id);

    // --- Senha ---
    void updatePassword(UUID id, String newHashedPassword);
}
