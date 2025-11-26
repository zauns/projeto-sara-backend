package sara.projeto.saraEmprega.adapter;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.UserRepositoryPort;
import sara.projeto.saraEmprega.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter extends ContaRepositoryAdapter<User> implements UserRepositoryPort{

    //adicione novas funções caso não existam em ContaRepositoryAdapter

    private final UserRepository repositorio;

    @Override
    protected JpaRepository<User, UUID> getRepositorio() {
        return repositorio;
    }

    @Override
    public User salvar(User user) {
        return super.salvar(user);
    }

	@Override
	public Optional<User> encontrarPorEmail(String email) {
	    return repositorio.findByEmail(email);
	}

}
