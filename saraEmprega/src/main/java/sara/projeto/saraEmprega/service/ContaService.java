package sara.projeto.saraEmprega.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sara.projeto.saraEmprega.DTO.ContaResponseDTO;
import sara.projeto.saraEmprega.DTO.SecretariaRequestDTO;
import sara.projeto.saraEmprega.model.Conta;
import sara.projeto.saraEmprega.model.Secretaria;
import sara.projeto.saraEmprega.repository.ContaRepository;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public ContaResponseDTO criarConta(SecretariaRequestDTO dto) {
        Secretaria secretaria = new Secretaria();
        secretaria.setNome(dto.nome());
        secretaria.setEmail(dto.email());
        secretaria.setSenha(dto.senha());
        secretaria.setTelefone(dto.telefone());
        secretaria.setEndereco(dto.endereco());

        Secretaria secretariaSalva = contaRepository.save(secretaria);

        return new ContaResponseDTO(secretariaSalva);
    }
}
