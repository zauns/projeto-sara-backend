package sara.emprega.msusers.ports;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sara.emprega.msusers.exception.UserNotFoundException;
import sara.emprega.msusers.model.Curriculum;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.repository.UserRepository;

import java.util.UUID;

@AllArgsConstructor
@Component
public class UserRepositoryPort {
    private UserRepository userRepository;

    public User findByID(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(()
                -> new UserNotFoundException("Usuário não encontrado" ));
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    //funcao update, deve ser chamada somente para update, nao para create
    public User update(User user) {
        return userRepository.save(user);
    }

    public User findByMail(String username) {
        return userRepository.findUserByEmail(username);
    }

}
