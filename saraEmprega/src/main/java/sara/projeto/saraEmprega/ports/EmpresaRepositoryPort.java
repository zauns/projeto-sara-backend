package sara.projeto.saraEmprega.ports;

import java.util.List;
import sara.projeto.saraEmprega.model.Empresa;

public interface EmpresaRepositoryPort extends ContaRepositoryPort<Empresa> {
    
    List<Empresa> findByIsValidadaFalse(); 
}