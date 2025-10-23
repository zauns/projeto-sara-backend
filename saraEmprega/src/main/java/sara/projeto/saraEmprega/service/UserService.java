package sara.projeto.saraEmprega.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.UserServicePort;

import java.util.List;
import java.util.UUID;

//TODO
@Service
@Transactional
public class UserService implements UserServicePort {

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
    public User logoutUser() {
        return null;
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
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
