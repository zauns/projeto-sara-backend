package sara.projeto.saraEmprega.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import sara.projeto.saraEmprega.model.Conta;

public interface ContaRepositoryPort {
    Conta salvar(Conta conta);
    Optional<Conta> encontrarPorId(UUID id);
    Optional<Conta> encontrarPorEmail(String email);
    List<Conta> encontarTudo();
    void deletarPorId(UUID id);
    boolean exists(UUID id);
}
