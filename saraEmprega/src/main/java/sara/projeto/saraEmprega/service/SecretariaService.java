package sara.projeto.saraEmprega.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.SecretariaRequestDTO;
import sara.projeto.saraEmprega.model.Secretaria;
import sara.projeto.saraEmprega.ports.ContaRepositoryPort;
import sara.projeto.saraEmprega.ports.SecretariaServicePort;

@Service
@RequiredArgsConstructor
public class SecretariaService extends ContaService<Secretaria> implements SecretariaServicePort {

    private final ContaRepositoryPort<Secretaria> repositorio;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected ContaRepositoryPort<Secretaria> repositorio() {
        return repositorio;
    }

    @Override
    @Transactional
    public ContaResponseDTO criar(SecretariaRequestDTO dto) {
        Secretaria secretaria = new Secretaria();
        mapToSecretaria(dto, secretaria);
        Secretaria novaSecretaria = repositorio.salvar(secretaria);
        return new ContaResponseDTO(novaSecretaria);
    }

    @Override
    @Transactional
    public ContaResponseDTO atualizar(UUID id, SecretariaRequestDTO dto) {
        Secretaria secretaria = repositorio.encontrarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Secretaria não encontrada com o ID: " + id));
        mapToSecretaria(dto, secretaria);
        Secretaria atualizada = repositorio.salvar(secretaria);
        return new ContaResponseDTO(atualizada);
    }

    private void mapToSecretaria(SecretariaRequestDTO dto, Secretaria secretaria) {
        secretaria.setNome(dto.nome());
        secretaria.setEmail(dto.email());
        secretaria.setTelefone(dto.telefone());
        secretaria.setEndereco(dto.endereco());
        secretaria.setSenhaHash(passwordEncoder.encode(dto.senha()));
        secretaria.setMunicipio(dto.municipio());
        secretaria.setValidada(false);
    }

}
