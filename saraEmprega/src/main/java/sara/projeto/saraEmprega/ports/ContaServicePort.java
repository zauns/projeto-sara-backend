package sara.projeto.saraEmprega.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.model.Conta;

/**
 * Interface base para serviços de operações com Conta
 * Define operações básicas que todos os serviços de conta devem implementar
 *
 * Como usar:
 * - Crie uma interface específica (ex: UserServicePort) que estenda esta
 * - Adicione métodos específicos do tipo na interface especializada
 * - Implemente a interface especializada no serviço concreto
 *
 * Esta interface contém apenas operações genéricas aplicáveis a todos os tipos de conta
 * Operações específicas devem ser definidas nas interfaces especializadas
 */
public interface ContaServicePort {
    ContaResponseDTO buscarPorId(UUID id);
    Optional<Conta> buscarPorEmail(String email);
    List<ContaResponseDTO> buscarTodasAsContas();
    void excluirConta(UUID id);
}
