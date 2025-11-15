package sara.projeto.saraEmprega.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import sara.projeto.saraEmprega.model.Conta;
import sara.projeto.saraEmprega.model.Empresa;

public interface ContaRepositoryPort<T extends Conta> {
    T salvar(T conta);
    Optional<T> encontrarPorId(UUID id);
    Optional<T> encontrarPorEmail(String email);
    boolean existePorEmail(String email);
    boolean existePorId(UUID id);
    List<T> encontrarTodas();
    void deletar(UUID id);
    Optional<Empresa> findByIsValidadaFalse();
}
                                                                                                                                                            