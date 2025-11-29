package sara.projeto.saraEmprega.repository;

import java.util.List;
import sara.projeto.saraEmprega.model.Empresa;

public interface EmpresaRepository extends ContaRepository<Empresa> {
     List<Empresa> findByIsValidadaFalse();
}
