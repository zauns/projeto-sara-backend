package sara.projeto.saraEmprega.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import sara.projeto.saraEmprega.model.Empresa;
import sara.projeto.saraEmprega.repository.EmpresaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmpresaRepositoryAdapter extends ContaRepositoryAdapter<Empresa> {

    private final EmpresaRepository repositorio;

    @Override
    protected JpaRepository<Empresa, UUID> getRepositorio() {
        return repositorio;
    }

    @Override
    public Optional<Empresa> encontrarPorEmail(String email) {
        return repositorio.findByEmail(email);
    }
    
    @Override
    public Empresa salvar(Empresa conta) {
        return super.salvar(conta);
    }

    @Override
    public boolean existePorEmail(String email) {
        return super.existePorEmail(email);
    }

    @Override
    public List<Empresa> encontrarTodas(){
        return super.encontrarTodas();
    }
    
}