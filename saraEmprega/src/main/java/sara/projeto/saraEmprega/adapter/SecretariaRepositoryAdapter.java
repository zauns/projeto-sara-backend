package sara.projeto.saraEmprega.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import sara.projeto.saraEmprega.model.Secretaria;
import sara.projeto.saraEmprega.repository.SecretariaRepository;

@Component
@RequiredArgsConstructor
public class SecretariaRepositoryAdapter extends ContaRepositoryAdapter<Secretaria> {

    private final SecretariaRepository repositorio;

    @Override
    protected JpaRepository<Secretaria, UUID> getRepositorio() {
        return repositorio;
    }

    @Override
    public Secretaria salvar(Secretaria conta) {
        return super.salvar(conta);
    }

    @Override
    public boolean existePorEmail(String email) {
        return super.existePorEmail(email);
    }

    @Override
    public List<Secretaria> encontrarTodas() {
        return super.encontrarTodas();
    }

    @Override
    public Optional<Secretaria> encontrarPorEmail(String email) {
        return repositorio.findByEmail(email);
    }

}
