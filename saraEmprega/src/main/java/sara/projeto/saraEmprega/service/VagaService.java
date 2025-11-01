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
import sara.projeto.saraEmprega.repository.VagaRepository;

@Service
public class VagaService {
    
    private final VagaRepository vagaRepository;
    private final EmpresaService empresaService;

    public VagaService(VagaRepository vagaRepository, EmpresaService empresaService) {
        this.vagaRepository = vagaRepository;
        this.empresaService = empresaService;
    }

    //CRIAR VAGA
    @Transactional
    public VagaResponseDTO criarVaga(VagaRequestDTO dto){
        Vaga vaga = new Vaga();
        mapToVaga(dto, vaga);

        Vaga vagaSalva = vagaRepository.save(vaga);
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
        empresaService.buscarPorId(empresaId); // Verifica se a empresa existe

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

    //FUNÇÕES AUXILIARES
    private void mapToVaga(
        VagaRequestDTO dto,
        Vaga vaga
    ) {
        vaga.setTitulo(dto.titulo());
        vaga.setDescricao(dto.descricao());

        Empresa empresa = empresaService.buscarEmpresaPorId(dto.empresaId());
        
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
