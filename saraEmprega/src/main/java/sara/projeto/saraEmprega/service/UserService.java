package sara.projeto.saraEmprega.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sara.projeto.saraEmprega.dto.UserDTO;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.UserRepositoryPort;
import sara.projeto.saraEmprega.ports.UserServicePort;
import sara.projeto.saraEmprega.util.jwt.UserAuthenticated;
import sara.projeto.saraEmprega.util.user_statagy.UpdateContext;

import java.util.Optional;
import java.util.UUID;

//TODO
@Service
@Transactional
@AllArgsConstructor
public class UserService implements UserServicePort {

    private final UserRepositoryPort userRepository;
    private final UpdateContext updateContext;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Usuária não encontrada!"));
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuária não encontrada!"));
    }


    @Override
    public User updateUser(UserDTO userDTO, String mail) {
        User user = findByEmail(mail);
        updateContext.execute(user, userDTO);
        return userRepository.save(user);
    }

    public User updateCurriculum(User user) {
        return userRepository.save(user);
    }

    public User createUser(User user) {
        if(!userRepository.existsByEmail(user.getEmail())){
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("usuario mal formatado");
    }

    /*
    @Override
    public void updateUserRoles(UUID id, List<String> roles) {

    }

    @Override
    public void deleteUserById(UUID id) {

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
