package sara.projeto.saraEmprega.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import sara.projeto.saraEmprega.dto.UserDTO;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.UserRepositoryPort;
import sara.projeto.saraEmprega.ports.UserServicePort;
import sara.projeto.saraEmprega.util.user_statagy.UpdateContext;

//TODO
@Service
@Transactional
@AllArgsConstructor
public class UserService implements UserServicePort {

    UserRepositoryPort userRepository;
    UpdateContext updateContext;

    // Estou assumindo que os nomes do UserServicPort são os corretos

    @Override
    public User getUserByMail(String mail) {
    return userRepository.getUserByEmail(mail);
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User updateUser(UserDTO userDTO, String email) {
        User user = userRepository.getUserByEmail(email);
        updateContext.execute(user, userDTO);
        userRepository.update(user);
        return user;
    }

    public User curriculumUpdate(User user) {
        return userRepository.update(user);
    }

    @Override
    public User createUser(String claim, User user) {
        if (claim.contains("ROLE_SECRETARY")) {
            return userRepository.create(user);
        }
        throw new IllegalArgumentException("usuario mal formatado");
    }
}

/*
 * @Override
 * public void updateUserRoles(UUID id, List<String> roles) {
 *
 * }
 *
 * @Override
 * public void deleteUserById(UUID id) {
 *
 * }
 *
 * @Override
 * public void deleteUserByMail(String mail) {
 *
 * }
 *
 * @Override
 * public boolean existsByMail(String mail) {
 * return false;
 * }
 */
