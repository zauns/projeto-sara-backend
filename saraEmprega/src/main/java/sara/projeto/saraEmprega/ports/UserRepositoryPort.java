package sara.projeto.saraEmprega.ports;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sara.projeto.saraEmprega.exception.UserNotFoundException;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.repository.UserRepository;

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
