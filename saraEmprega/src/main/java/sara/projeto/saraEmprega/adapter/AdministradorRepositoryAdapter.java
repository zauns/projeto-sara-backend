package sara.projeto.saraEmprega.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import sara.projeto.saraEmprega.model.Administrador;
import sara.projeto.saraEmprega.repository.AdministradorRepository;

@Component
@RequiredArgsConstructor
public class AdministradorRepositoryAdapter extends ContaRepositoryAdapter<Administrador> {

    private final AdministradorRepository repositorio;

    @Override
    protected JpaRepository<Administrador, UUID> getRepositorio() {
        return repositorio;
    }

    @Override
    public Administrador salvar(Administrador conta) {
        return getRepositorio().save(conta);
    }

    @Override
    public boolean existePorEmail(String email) {
        return encontrarPorEmail(email).isPresent();
    }

    @Override
    public List<Administrador> encontrarTodas(){
        return getRepositorio().findAll();
    }

    @Override
    public void deletar(UUID id){
        getRepositorio().deleteById(id);
    }

	@Override
	public boolean existePorId(UUID id) {
		return encontrarPorId(id).isPresent();
	}

    @Override
    public Optional<Administrador> encontrarPorEmail(String email) {
        return repositorio.findByEmail(email);
    }

}
