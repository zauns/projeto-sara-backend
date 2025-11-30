package sara.projeto.saraEmprega.repository;

import java.util.List;

import sara.projeto.saraEmprega.model.Secretaria;

public interface SecretariaRepository extends ContaRepository<Secretaria> {
    List<Secretaria> findByIsValidadaFalse();
}
