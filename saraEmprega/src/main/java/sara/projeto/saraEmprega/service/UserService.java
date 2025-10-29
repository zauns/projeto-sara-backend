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
    public User updateUser(UserDTO userDTO, UserAuthenticated userAuth) {
        UUID userID = userAuth.getUser().getId();
        User user = userRepository.findByID(userID);
        updateContext.execute(user, userDTO);
        userRepository.update(user);
        return user;
    }

    public User curriculumUpdate(User user) {
        return userRepository.update(user);
    }

    public User CreateUser(UserAuthenticated auth, User user) {
        if(auth.getAuthorities().contains("ROLE_SECRETARY")){
            return userRepository.create(user);
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

    @Override
    public boolean existsById(UUID id) {
        return false;
    }
*/

}
