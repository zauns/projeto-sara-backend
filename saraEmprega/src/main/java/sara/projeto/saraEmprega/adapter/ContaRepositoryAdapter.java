package sara.projeto.saraEmprega.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import sara.projeto.saraEmprega.model.Conta;
import sara.projeto.saraEmprega.ports.ContaRepositoryPort;

@RequiredArgsConstructor
public abstract class ContaRepositoryAdapter<T extends Conta> implements ContaRepositoryPort<T> {

    protected abstract JpaRepository<T, UUID> getRepositorio();

    @Override
    public T salvar(T conta) {
        return getRepositorio().save(conta);
    }

    @Override
    public Optional<T> encontrarPorId(UUID id) {
        return getRepositorio().findById(id);
    }

    @Override
    public boolean existePorEmail(String email) {
        return encontrarPorEmail(email).isPresent();
    }

    @Override
    public List<T> encontrarTudo(){
        return getRepositorio().findAll();
    }

    @Override
    public void deletarPorId(UUID id){
        getRepositorio().deleteById(id);
    }

	@Override
	public boolean existePorId(UUID id) {
		return encontrarPorId(id).isPresent();
	}
}
