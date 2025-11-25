package sara.projeto.saraEmprega.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import sara.projeto.saraEmprega.dto.VagaRequestDTO;
import sara.projeto.saraEmprega.dto.VagaResponseDTO;
import sara.projeto.saraEmprega.model.Empresa;
import sara.projeto.saraEmprega.model.Vaga;
import sara.projeto.saraEmprega.ports.EmpresaRepositoryPort;
import sara.projeto.saraEmprega.ports.VagaRepositoryPort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt; 
import org.springframework.security.access.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final VagaRepositoryPort vagaRepositoryPort;
    private final EmpresaRepositoryPort empresaRepository;


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
        return vagaRepositoryPort
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

        return vagaRepositoryPort
            .findByEmpresaId(empresaId)
            .stream()
            .map(VagaResponseDTO::new)
            .toList();
    }

    @Transactional
    public void excluirVaga(UUID id){
        if(!vagaRepositoryPort.existsById(id)){
            throw new EntityNotFoundException(
                "Vaga não encontrada com o ID: " + id
            );
        }
        vagaRepositoryPort.delete(id);
    }

    //ATUALIZAR VAGA
    @Transactional
    public VagaResponseDTO atualizarVaga(
        UUID id,
        VagaRequestDTO dto
    ) {
        Vaga vaga = buscarVaga(id);
        mapToVaga(dto, vaga);
        Vaga vagaAtualizada = vagaRepositoryPort.save(
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

    //ALTERAR ISATIVA
    @Transactional
    public VagaResponseDTO mudarStatusAtivacao(UUID id, boolean isAtiva) {
        Vaga vaga = buscarVaga(id); 

        // Define o novo status
        vaga.setAtiva(isAtiva);

        // Salva e retorna o DTO
        Vaga vagaAtualizada = vagaRepositoryPort.save(vaga);
        return new VagaResponseDTO(vagaAtualizada);
    }

    //PESQUISAR VAGAS (POR TERMO PRESENTE NO TÍTULO OU DESCRIÇÃO)
    @Transactional(readOnly = true)
    public List<VagaResponseDTO> buscarVagasPorTermo(String termo) {
        if (termo == null || termo.isBlank()) {
            return buscarTodasAsVagas();
        }
        
        // Remove espaços desnecessários e usa o termo para ambas as buscas
        String termoLimpo = termo.trim();

        return vagaRepositoryPort
            .findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(termoLimpo, termoLimpo)
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
        vaga.setAtiva(dto.isAtiva());
        Empresa empresa = empresaRepository.encontrarPorId(dto.empresaId())
            .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com o ID: " + dto.empresaId()));

        vaga.setEmpresa(empresa);
    }

    private Vaga buscarVaga(UUID id) {
        return vagaRepositoryPort
            .findById(id)
            .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Vaga com id " + id + " não encontrada"
                    )
            );
    }

    private void verificarPropriedade(Vaga vaga) {
        UUID empresaIdVaga = vaga.getEmpresa().getId();
        UUID empresaIdLogada = getEmpresaIdLogada();

        // Lança exceção se o ID da vaga for diferente do ID do usuário logado
        if (!empresaIdVaga.equals(empresaIdLogada)) {
            throw new AccessDeniedException("Acesso negado: Você não tem permissão para alterar esta vaga.");
        }
    }

    private UUID getEmpresaIdLogada() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (!(principal instanceof Jwt)) {
            // Este caso só deve ser atingido se o @PreAuthorize falhar
            throw new AccessDeniedException("Usuário não autenticado via token JWT válido.");
        }
        
        Jwt jwt = (Jwt) principal;
        
        String empresaIdString = jwt.getClaimAsString("userId");
        
        if (empresaIdString == null) {
             throw new AccessDeniedException("Token JWT não possui a claim 'userId' necessária.");
        }
        
        return UUID.fromString(empresaIdString);
    }
}
