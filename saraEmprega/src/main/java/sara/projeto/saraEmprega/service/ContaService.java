package sara.projeto.saraEmprega.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sara.projeto.saraEmprega.DTO.ContaResponseDTO;
import sara.projeto.saraEmprega.DTO.SecretariaRequestDTO;
import sara.projeto.saraEmprega.model.Conta;
import sara.projeto.saraEmprega.model.Secretaria;
import sara.projeto.saraEmprega.repository.ContaRepository;


@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Transactional
    public ContaResponseDTO criarConta(SecretariaRequestDTO dto) {
        // sobrecarregar este método para outros tipos
        Secretaria secretaria = new Secretaria();
        mapToSecretaria(dto, secretaria);
        

        Secretaria secretariaSalva = contaRepository.save(secretaria);
        return new ContaResponseDTO(secretariaSalva);
    }

    @Transactional(readOnly = true)
    public ContaResponseDTO buscarContaPorId(UUID id) {
        Conta conta = buscarConta(id);
        return new ContaResponseDTO(conta);
    }

    @Transactional(readOnly = true)
    public List<ContaResponseDTO> buscarTodasAsContas() {
        return contaRepository.findAll().stream().map(ContaResponseDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public void excluirConta(UUID id) {
        if (!contaRepository.existsById(id)) {
            throw new EntityNotFoundException("Conta não encontrada com o ID: " + id);
        }
        contaRepository.deleteById(id);
    }

    @Transactional
    public ContaResponseDTO atualizarSecretaria(UUID id, SecretariaRequestDTO dto) {
        Conta conta = buscarConta(id);

        if (!(conta instanceof Secretaria secretariaExistente)) {
            throw new IllegalArgumentException("A conta com o ID " + id + " não é uma Secretaria.");
        }

        mapToSecretaria(dto, secretariaExistente);

        Secretaria secretariaAtualizada = contaRepository.save(secretariaExistente);

        return new ContaResponseDTO(secretariaAtualizada);
    }

    //funções auxiliares

    private void mapToSecretaria(SecretariaRequestDTO dto, Secretaria secretaria){
        secretaria.setNome(dto.nome());
        secretaria.setEmail(dto.email());
        secretaria.setTelefone(dto.telefone());
        secretaria.setEndereco(dto.endereco());
        secretaria.setSenha(dto.senha());
    }

    private Conta buscarConta(UUID id){
        return contaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Conta não encontrada com o ID: " + id));
    }
}
