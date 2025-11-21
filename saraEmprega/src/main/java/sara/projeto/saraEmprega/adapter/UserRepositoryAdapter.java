package sara.projeto.saraEmprega.adapter;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.UserRepositoryPort;
import sara.projeto.saraEmprega.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository repository;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    // @Override
    // public Optional<User> findById(UUID id) {
    //     return repository.findById(id);
    // }

    // @Override
    // public Optional<User> findByEmail(String email) {
    //     return repository.findByEmail(email);
    // }

    @Override
	public Optional<User> getUserById(UUID id) {
	    return repository.findById(id);
	}

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

	@Override
	public User getUserByEmail(String email) {
	    return repository.findByEmail(email);
	}

	@Override
	public User update(User user) {
	    return repository.save(user);
	}

	@Override
	public User create(User user) {
	    return repository.save(user);
	}

}
