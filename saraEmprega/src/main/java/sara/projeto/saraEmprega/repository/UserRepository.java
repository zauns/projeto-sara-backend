package sara.projeto.saraEmprega.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sara.projeto.saraEmprega.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String username);
    boolean existsByEmail(String email);
}
