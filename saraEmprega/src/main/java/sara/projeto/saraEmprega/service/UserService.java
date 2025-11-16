package sara.projeto.saraEmprega.service;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sara.projeto.saraEmprega.dto.UserDTO;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.UserRepositoryPort;
import sara.projeto.saraEmprega.ports.UserServicePort;
import sara.projeto.saraEmprega.util.user_statagy.UpdateContext;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements UserServicePort {

    private final UserRepositoryPort userRepository;
    private final UpdateContext updateContext;

    @Override
    public User findByEmail(String email) {
        return userRepository
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuária não encontrada!"));
    }

    @Override
    public User findById(UUID id) {
        return userRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Usuária não encontrada!"));
    }

    @Override
    public User updateUser(UserDTO userDTO, UUID id) {
        User user = findById(id);
        updateContext.execute(user, userDTO);
        return userRepository.save(user);
    }

    public User updateCurriculum(User user) {
        return userRepository.save(user);
    }

    public User createUser(User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("usuario mal formatado");
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void deleteUserById(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado"); // ou EntityNotFoundException
        }
        userRepository.deleteById(id);
    }

    /*
    @Override
    public void updateUserRoles(UUID id, List<String> roles) {

    }

    @Override
    public void deleteUserByMail(String mail) {

    }

    @Override
    public boolean existsByMail(String mail) {
        return false;
    }
*/
}
