package sara.projeto.saraEmprega.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sara.projeto.saraEmprega.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User getUserById(UUID id);


    Optional<User> findUserByEmail(String username);
}
