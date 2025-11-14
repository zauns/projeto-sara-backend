package sara.projeto.saraEmprega.ports;

import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sara.projeto.saraEmprega.exception.UserNotFoundException;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.repository.UserRepository;

public interface UserRepositoryPort {
    User save(User user); //salva e atualiza mas pode separar pra ficar mais congruente
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    void deleteById(UUID id);
}
