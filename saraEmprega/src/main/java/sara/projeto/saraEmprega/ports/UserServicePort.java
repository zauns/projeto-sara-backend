package sara.projeto.saraEmprega.ports;

import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.UserRequestDTO;
import sara.projeto.saraEmprega.model.User;

import java.util.UUID;

public interface UserServicePort extends ContaServicePort { //adapta as outras funções do ContaServicePort para o tipo user

    ContaResponseDTO create(UserRequestDTO dto);

    ContaResponseDTO update(UUID id, UserRequestDTO dto);

    User getUserByMail(String email);

    User curriculumUpdate(User user);

    UserRequestDTO getDados(UUID id);
    //curriculum jaja

    // Optional<User> getUserById(UUID id);
    // User updateUser(UserDTO userDTO, String email);
    // User createUser(String claim, User user);
    // User curriculumUpdate(User user);
    // User updateCurriculum(User user);
    // boolean existsByEmail(String email);
    // void deleteUserById(UUID id);
    /*
     * void updateUserRoles(UUID id, List<String> roles);
     *
     * // --- Exclusão ---
     * void deleteUserByMail(String mail);
     *
     * // --- Verificação / Existência ---
     * boolean existsByMail(String mail);
     * boolean existsById(UUID id);
     */
}
