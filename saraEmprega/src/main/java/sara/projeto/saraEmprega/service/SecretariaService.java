package sara.projeto.saraEmprega.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.SecretariaRequestDTO;
import sara.projeto.saraEmprega.model.Secretaria;
import sara.projeto.saraEmprega.repository.SecretariaRepository;

@Service
public class SecretariaService extends ContaService<Secretaria> {

    @Autowired
    private SecretariaRepository secretariaRepository;

    @Override
    protected JpaRepository<Secretaria, UUID> repositorio() {
        return secretariaRepository;
    }

    @Transactional
    public ContaResponseDTO criar(SecretariaRequestDTO dto) {
        Secretaria secretaria = new Secretaria();
        mapToSecretaria(dto, secretaria);
        Secretaria novaSecretaria = secretariaRepository.save(secretaria);
        return new ContaResponseDTO(novaSecretaria);
    }

    @Transactional
    public ContaResponseDTO atualizar(UUID id, SecretariaRequestDTO dto) {
        Secretaria secretaria = secretariaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Secretaria não encontrada com o ID: " + id));
        mapToSecretaria(dto, secretaria);
        Secretaria atualizada = secretariaRepository.save(secretaria);
        return new ContaResponseDTO(atualizada);
    }

    private void mapToSecretaria(SecretariaRequestDTO dto, Secretaria secretaria) {
        secretaria.setNome(dto.nome());
        secretaria.setEmail(dto.email());
        secretaria.setTelefone(dto.telefone());
        secretaria.setEndereco(dto.endereco());
        secretaria.setSenhaHash(dto.senha());
        secretaria.setMunicipio(dto.municipio());
    }
}
