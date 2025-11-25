package sara.projeto.saraEmprega.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; 
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sara.projeto.saraEmprega.dto.CandidaturaRequestDTO;
import sara.projeto.saraEmprega.dto.CandidaturaResponseDTO;
import sara.projeto.saraEmprega.enums.StatusCandidatura;
import sara.projeto.saraEmprega.ports.CandidaturaServicePort;

@RestController
@RequestMapping("/candidaturas")
@RequiredArgsConstructor
public class CandidaturaController {

    private final CandidaturaServicePort candidaturaServicePort;

    // --- 1. CRIAÇÃO ---

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CandidaturaResponseDTO> criar(
            @Valid @RequestBody CandidaturaRequestDTO dto) { 
        
        CandidaturaResponseDTO novaCandidatura = candidaturaServicePort.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCandidatura);
    }

    // --- 2. DESISTÊNCIA --- 
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> desistir(
            @PathVariable("id") UUID candidaturaId) {

        candidaturaServicePort.desistir(candidaturaId);
        
        return ResponseEntity.noContent().build(); 
    }

    // --- 3. BUSCAS (CANDIDATO e EMPRESA) ---

    @GetMapping("/minhas")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CandidaturaResponseDTO>> buscarMinhasCandidaturas() {
        List<CandidaturaResponseDTO> candidaturas = candidaturaServicePort.buscarMinhasCandidaturas();
        
        return ResponseEntity.ok(candidaturas);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'EMPRESA', 'ADMIN', 'SUPER_ADMIN')") 
    public ResponseEntity<CandidaturaResponseDTO> buscarPorId(@PathVariable UUID id) {
        
        CandidaturaResponseDTO candidatura = candidaturaServicePort.buscarPorId(id);
        
        return ResponseEntity.ok(candidatura);
    }

    @GetMapping("/vaga/{vagaId}")
    @PreAuthorize("hasAnyRole('EMPRESA', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<CandidaturaResponseDTO>> buscarPorVaga(@PathVariable UUID vagaId) {
        
        List<CandidaturaResponseDTO> candidaturas = candidaturaServicePort.buscarPorVagaId(vagaId);
        
        return ResponseEntity.ok(candidaturas);
    }
    
    // Busca por Status (Geralmente admin/super_admin)
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')") 
    public ResponseEntity<List<CandidaturaResponseDTO>> buscarPorStatus(@PathVariable StatusCandidatura status) {
        
        List<CandidaturaResponseDTO> candidaturas = candidaturaServicePort.buscarPorStatus(status);
        
        return ResponseEntity.ok(candidaturas);
    }

    // --- 4. AÇÃO DA EMPRESA (Atualização de Status) ---

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('EMPRESA', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<CandidaturaResponseDTO> atualizarStatus(
            @PathVariable UUID id,
            @RequestParam StatusCandidatura status) {
        
        CandidaturaResponseDTO atualizada = candidaturaServicePort.atualizarStatus(id, status);
        
        return ResponseEntity.ok(atualizada);
    }
}