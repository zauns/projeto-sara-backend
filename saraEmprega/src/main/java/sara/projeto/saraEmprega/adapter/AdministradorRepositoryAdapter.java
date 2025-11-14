package sara.projeto.saraEmprega.adapter;

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
}
