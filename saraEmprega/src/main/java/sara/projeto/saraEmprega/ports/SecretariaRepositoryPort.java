package sara.projeto.saraEmprega.ports;

import java.util.List;
import sara.projeto.saraEmprega.model.Secretaria;

public interface SecretariaRepositoryPort extends ContaRepositoryPort<Secretaria> {
    
    List<Secretaria> findByIsValidadaFalse();
}