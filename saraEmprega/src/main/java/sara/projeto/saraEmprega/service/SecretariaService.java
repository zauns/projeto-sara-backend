package sara.projeto.saraEmprega.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.SecretariaRequestDTO;
import sara.projeto.saraEmprega.model.Secretaria;
import sara.projeto.saraEmprega.ports.ContaRepositoryPort;
import sara.projeto.saraEmprega.ports.SecretariaRepositoryPort;
import sara.projeto.saraEmprega.ports.SecretariaServicePort;
import sara.projeto.saraEmprega.util.Mapper;

@Service
@RequiredArgsConstructor
public class SecretariaService extends ContaService<Secretaria> implements SecretariaServicePort {

    private final SecretariaRepositoryPort repositorio;
    private final Mapper mapper;

    @Override
    protected ContaRepositoryPort<Secretaria> repositorio() {
        return repositorio;
    }

    @Override
    @Transactional
    public ContaResponseDTO criar(SecretariaRequestDTO dto) {
        Secretaria secretaria = new Secretaria();
        secretaria = mapper.secretariaParaEntidade(dto);
        Secretaria novaSecretaria = repositorio.salvar(secretaria);
        return new ContaResponseDTO(novaSecretaria);
    }

    @Override
    @Transactional
    public ContaResponseDTO atualizar(UUID id, SecretariaRequestDTO dto) {
        Secretaria secretaria = repositorio.encontrarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Secretaria não encontrada com o ID: " + id));
        mapper.atualizaSecretariaDeDTO(dto, secretaria);
        Secretaria atualizada = repositorio.salvar(secretaria);
        return new ContaResponseDTO(atualizada);
    }

    @Transactional
    public List<ContaResponseDTO> getSecretariasNaoValidadas() {
        return repositorio.findByIsValidadaFalse()
            .stream()
            .map(ContaResponseDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public ContaResponseDTO aprovarSecretaria(UUID id) {
        Secretaria secretaria = repositorio.encontrarPorId(id)
            .orElseThrow(() -> new EntityNotFoundException("Secretaria não encontrada"));

        secretaria.setValidada(true);
        repositorio.salvar(secretaria);
        return new ContaResponseDTO(secretaria);
    }

    @Transactional
    public SecretariaRequestDTO getDados(UUID id){
        Secretaria secretaria = repositorio.encontrarPorId(id).get();
        return SecretariaRequestDTO.converter(secretaria);
    }

}
