package sara.emprega.msusers.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sara.emprega.msusers.dto.UserDTO;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.ports.UserRepositoryPort;
import sara.emprega.msusers.ports.UserServicePort;
import sara.emprega.msusers.util.user_concurrency.strategy.UserUpdateContext;
import sara.emprega.msusers.util.user_strategy.UpdateContext;
import java.util.UUID;

//TODO
@Service
@Transactional
@AllArgsConstructor
public class UserService implements UserServicePort {

    UserRepositoryPort userRepository;
    UpdateContext updateContext;

    @Override
    public User getUserByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    @Override
    public User getUserById(UUID id) {
        return null;
    }

    @Override
    public User updateUser(UserDTO userDTO, String mail) {
        User user = userRepository.findByMail(mail);
        updateContext.execute(user, userDTO);
        userRepository.update(user);
        return user;
    }

    public User curriculumUpdate(User user) {
        return userRepository.update(user);
    }

    public User CreateUser(String claim, User user) {
        if(claim.contains("ROLE_SECRETARY")){
            return userRepository.create(user);
        }
        throw new IllegalArgumentException("usuario mal formatado");
    }
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


