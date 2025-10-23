package sara.emprega.msusers.ports;

import sara.emprega.msusers.model.User;
import java.util.UUID;
import java.util.List;


public interface UserServicePort {

    // --- Consultas ---
    User getUserByMail(String mail);
    User getUserById(UUID id);
    List<User> getAllUsers();
    List<User> getUsersByRole(String role);

    User logoutUser();
    // --- Create/update ---
    User createUser(User user);

    //TODO envio do usuario com alteracoes e altera o user com o msm ID
    User updateUser(User user);

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

