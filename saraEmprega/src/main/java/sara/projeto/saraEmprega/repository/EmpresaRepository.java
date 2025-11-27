package sara.projeto.saraEmprega.repository;

import java.util.List;
import java.util.Optional;
import sara.projeto.saraEmprega.model.Empresa;

public interface EmpresaRepository extends ContaRepository<Empresa> {
     Optional<Empresa> findByEmail(String email);
     List<Empresa> findByIsValidadaFalse();
}
