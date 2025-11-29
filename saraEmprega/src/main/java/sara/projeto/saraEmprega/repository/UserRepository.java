package sara.projeto.saraEmprega.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

import sara.projeto.saraEmprega.model.User;

@Repository
public interface UserRepository extends ContaRepository<User> {
    Optional<User> findById(UUID id); //esse método Precisa retornar um Optional
    boolean existsByEmail(String email);
}
