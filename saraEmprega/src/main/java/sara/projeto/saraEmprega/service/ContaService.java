package sara.projeto.saraEmprega.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.model.Conta;

@Service
public abstract class ContaService<T extends Conta> {


    protected abstract JpaRepository<T, UUID> repositorio();                                                                                                         

    @Transactional(readOnly = true)
    public ContaResponseDTO buscarPorId(UUID id) {
        T conta = repositorio().findById(id).orElseThrow(()
            -> new EntityNotFoundException("Conta não encontrada") //depois criar uma exceção específica para este caso
        );
        return new ContaResponseDTO(conta);
    }

    @Transactional(readOnly = true)
    public List<ContaResponseDTO> buscarTodasAsContas() {
        return repositorio()
            .findAll()
            .stream()
            .map(ContaResponseDTO::new) // lambda
            .collect(Collectors.toList());
    }

    @Transactional
    public void excluirConta(UUID id) {
        if (!repositorio().existsById(id)) {
            throw new EntityNotFoundException(
                "Conta não encontrada com o ID: " + id
            );
        }
        repositorio().deleteById(id);
    }

}