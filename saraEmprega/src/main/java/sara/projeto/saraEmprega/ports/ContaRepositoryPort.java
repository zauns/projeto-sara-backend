package sara.projeto.saraEmprega.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import sara.projeto.saraEmprega.model.Conta;

public interface ContaRepositoryPort<T extends Conta> {
    T salvar(T conta);
    Optional<T> encontrarPorId(UUID id);
    Optional<T> encontrarPorEmail(String email);
    boolean existePorEmail(String email);
    boolean existePorId(UUID id);
    List<T> encontrarTudo();
    void deletarPorId(UUID id);
}
