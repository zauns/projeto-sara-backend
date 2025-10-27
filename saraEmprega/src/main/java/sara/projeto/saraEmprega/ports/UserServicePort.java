package sara.projeto.saraEmprega.ports;

<<<<<<< HEAD:saraEmprega/src/main/java/sara/projeto/saraEmprega/ports/UserServicePort.java
import sara.projeto.saraEmprega.model.User;
=======
import sara.emprega.msusers.dto.UserDTO;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.util.jwt.UserAuthenticated;

>>>>>>> 06f735752fbcc028c83f7c3fb527063abf02ce34:ms-users/src/main/java/sara/emprega/msusers/ports/UserServicePort.java
import java.util.UUID;
import java.util.List;


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

