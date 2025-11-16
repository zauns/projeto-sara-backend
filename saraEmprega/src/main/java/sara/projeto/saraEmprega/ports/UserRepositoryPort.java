package sara.projeto.saraEmprega.ports;

import java.util.Optional;
import java.util.UUID;
import sara.projeto.saraEmprega.model.User;

public interface UserRepositoryPort {
    User save(User user); //salva e atualiza mas pode separar pra ficar mais congruente
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
