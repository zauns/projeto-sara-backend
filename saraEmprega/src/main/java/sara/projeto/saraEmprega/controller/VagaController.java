package sara.projeto.saraEmprega.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import sara.projeto.saraEmprega.dto.VagaRequestDTO;
import sara.projeto.saraEmprega.dto.VagaResponseDTO;
import sara.projeto.saraEmprega.service.VagaService;

@RestController
@RequestMapping("/vagas")
public class VagaController {
    
    @Autowired
    private VagaService vagaService;

    // --- MÉTODOS DE CRIAÇÃO E MODIFICAÇÃO  ---

    @PostMapping
    // Permite EMPRESA ou SUPER_ADMIN criar.
    @PreAuthorize("hasRole('EMPRESA') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<VagaResponseDTO> criarVaga(
        @Valid @RequestBody VagaRequestDTO dto
    ){
        VagaResponseDTO novaVaga = vagaService.criarVaga(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaVaga);
    }
    
    @PutMapping("/{id}")
    // Permite EMPRESA ou SUPER_ADMIN. A verificação de posse é feita no Service.
    @PreAuthorize("hasRole('EMPRESA') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<VagaResponseDTO> atualizarVaga(
        @PathVariable UUID id,
        @Valid @RequestBody VagaRequestDTO dto
    ){
        VagaResponseDTO vagaAtualizada = vagaService.atualizarVaga(
            id,
            dto
        );
        return ResponseEntity.ok(vagaAtualizada);
    }

    @PatchMapping("/{id}/status")
    // Permite EMPRESA ou SUPER_ADMIN. A verificação de posse é feita no Service.
    @PreAuthorize("hasRole('EMPRESA') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<VagaResponseDTO> mudarStatusAtivacao(
        @PathVariable UUID id,
        @RequestParam boolean isAtiva
    ) {
        VagaResponseDTO vagaAtualizada = vagaService.mudarStatusAtivacao(id, isAtiva); 
        return ResponseEntity.ok(vagaAtualizada);
    }

    @DeleteMapping("/{id}")
    // Permite EMPRESA ou SUPER_ADMIN. A verificação de posse é feita no Service.
    @PreAuthorize("hasRole('EMPRESA') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> excluirVaga(@PathVariable UUID id){
        vagaService.excluirVaga(id);
        return ResponseEntity.noContent().build();
    }
    
    // --- MÉTODOS DE BUSCA  ---
    
    @GetMapping("/{id}")
    public ResponseEntity<VagaResponseDTO> buscarVagaPorId(
        @PathVariable UUID id
    ){
        VagaResponseDTO vaga = vagaService.buscarVagaPorId(id);
        return ResponseEntity.ok(vaga);
    }

    @GetMapping
    public ResponseEntity<List<VagaResponseDTO>> buscarTodasAsVagas() {
        List<VagaResponseDTO> todasAsVagas = 
            vagaService.buscarTodasAsVagas();
        return ResponseEntity.ok(todasAsVagas);
    }

    @GetMapping("/por-empresa")
    public ResponseEntity<List<VagaResponseDTO>> buscarVagasPorEmpresa(
        @RequestParam UUID empresaId
    ){
      List<VagaResponseDTO> vagas = vagaService.buscarVagasPorEmpresa(empresaId);
      return ResponseEntity.ok(vagas);
    }

    // BUSCA POR TAGS (FILTROS)
    
    @GetMapping("/buscar/por-tag")
    public ResponseEntity<List<VagaResponseDTO>> buscarVagasPorUmaTag(
        @RequestParam String tag
    ) {
        List<VagaResponseDTO> vagas = vagaService.buscarVagasPorUmaTag(tag);
        return ResponseEntity.ok(vagas);
    }

    @GetMapping("/buscar/multiplas-tags")
    public ResponseEntity<List<VagaResponseDTO>> buscarVagasPorMultiplasTags(
        @RequestParam List<String> tags 
    ) {
        List<VagaResponseDTO> vagas = vagaService.buscarVagasPorMultiplasTags(tags);
        return ResponseEntity.ok(vagas);
    }
    
    //PESQUISAR VAGAS
    @GetMapping("/search")
    public ResponseEntity<List<VagaResponseDTO>> buscarVagasPorTermo(
        @RequestParam String termo
    ) {
        List<VagaResponseDTO> vagas = vagaService.buscarVagasPorTermo(termo);
        return ResponseEntity.ok(vagas);
    }
}