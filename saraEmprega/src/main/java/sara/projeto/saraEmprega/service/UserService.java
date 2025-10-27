package sara.projeto.saraEmprega.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sara.projeto.saraEmprega.dto.UserDTO;
import sara.projeto.saraEmprega.exception.UserNotFoundException;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.UserServicePort;
import sara.projeto.saraEmprega.repository.UserRepository;
import sara.projeto.saraEmprega.util.jwt.UserAuthenticated;
import sara.projeto.saraEmprega.util.user_statagy.UpdateContext;

import java.util.List;

import java.util.UUID;

//TODO
@Service
@Transactional
@AllArgsConstructor
public class UserService implements UserServicePort {

    UserRepository userRepository;
    UpdateContext updateContext;

    @Override
    public User getUserByMail(String mail) {
        return null;
    }

    @Override
    public User getUserById(UUID id) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return List.of();
    }



    @Override
    public User updateUser(UserDTO userDTO, UserAuthenticated userAuth) {
        UUID userID = userAuth.getUser().getId();
        User user = userRepository.findById(userID).orElseThrow(()
                -> new UserNotFoundException("Usuário não encontrado" ));
        updateContext.execute(user, userDTO);
        userRepository.save(user);
        return user;
    }

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

    @Override
    public void updatePassword(UUID id, String newHashedPassword) {

    }
}
