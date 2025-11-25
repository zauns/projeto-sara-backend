package sara.projeto.saraEmprega.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import sara.projeto.saraEmprega.dto.VagaRequestDTO;
import sara.projeto.saraEmprega.dto.VagaResponseDTO;
import sara.projeto.saraEmprega.model.Empresa;
import sara.projeto.saraEmprega.model.Vaga;
import sara.projeto.saraEmprega.ports.EmpresaRepositoryPort;
import sara.projeto.saraEmprega.repository.VagaRepository;

@Service
public class VagaService {

    private final VagaRepositoryPort vagaRepositoryPort;
    private final EmpresaRepositoryPort empresaRepository;

    public VagaService(VagaRepositoryPort vagaRepositoryPort, EmpresaRepositoryPort empresaRepository) {
        this.vagaRepositoryPort = vagaRepositoryPort;
        this.empresaRepository = empresaRepository;
    }

    //CRIAR VAGA
    @Transactional
    public VagaResponseDTO criarVaga(VagaRequestDTO dto){
        Vaga vaga = new Vaga();
        mapToVaga(dto, vaga);

        Vaga vagaSalva = vagaRepositoryPort.save(vaga);
        return new VagaResponseDTO(vagaSalva);
    }

    @Transactional(readOnly = true)
    public VagaResponseDTO buscarVagaPorId(UUID id){
        Vaga vaga = buscarVaga(id);
        return new VagaResponseDTO(vaga);
    }

    @Transactional(readOnly = true)
    public List<VagaResponseDTO> buscarTodasAsVagas(){
        return vagaRepository
            .findAll()
            .stream()
            .map(VagaResponseDTO::new)
            .toList();
    }

    @Transactional
    public List<VagaResponseDTO> buscarVagasPorEmpresa(UUID empresaId){
        if (!empresaRepository.existePorId(empresaId)) {
            throw new EntityNotFoundException("Empresa não encontrada com o ID: " + empresaId);
        } // Verifica se a empresa existe

        return vagaRepository
            .findByEmpresaId(empresaId)
            .stream()
            .map(VagaResponseDTO::new)
            .toList();
    }

    @Transactional
    public void excluirVaga(UUID id){
        if(!vagaRepository.existsById(id)){
            throw new EntityNotFoundException(
                "Vaga não encontrada com o ID: " + id
            );
        }
        vagaRepository.deleteById(id);
    }

    //ATUALIZAR VAGA
    @Transactional
    public VagaResponseDTO atualizarVaga(
        UUID id,
        VagaRequestDTO dto
    ) {
        Vaga vaga = buscarVaga(id);
        mapToVaga(dto, vaga);
        Vaga vagaAtualizada = vagaRepository.save(
            vaga
        );
        return new VagaResponseDTO(vagaAtualizada);
    }

    //Buscar por 1 tag
    @Transactional(readOnly = true)
    public List<VagaResponseDTO> buscarVagasPorUmaTag(String tag) {
        if (tag == null || tag.isBlank()) {
            return buscarTodasAsVagas(); 
        }

        return vagaRepositoryPort
            .findByTagsContaining(tag) 
            .stream()
            .map(VagaResponseDTO::new)
            .toList();
    }

    //Buscar por multiplas tags
    @Transactional(readOnly = true)
    public List<VagaResponseDTO> buscarVagasPorMultiplasTags(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return buscarTodasAsVagas(); 
        }

        List<String> tagsLimpa = tags.stream()
            .filter(tag -> tag != null && !tag.isBlank())
            .toList();
            
        if (tagsLimpa.isEmpty()) {
            return buscarTodasAsVagas();
        }

        return vagaRepositoryPort
            .findByTagsIn(tagsLimpa) 
            .stream()
            .map(VagaResponseDTO::new)
            .toList();
    }

    //FUNÇÕES AUXILIARES
    private void mapToVaga(
        VagaRequestDTO dto,
        Vaga vaga
    ) {
        vaga.setTitulo(dto.titulo());
        vaga.setDescricao(dto.descricao());
        vaga.setTags(dto.tags());
        vaga.setIsAtiva(dto.isAtiva());
        Empresa empresa = empresaRepository.encontrarPorId(dto.empresaId())
            .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com o ID: " + dto.empresaId()));

        vaga.setEmpresa(empresa);
    }

    private Vaga buscarVaga(UUID id) {
        return vagaRepository
            .findById(id)
            .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Vaga com id " + id + " não encontrada"
                    )
            );
    }
}
