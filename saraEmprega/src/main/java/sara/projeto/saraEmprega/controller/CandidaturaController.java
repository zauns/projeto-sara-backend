package sara.projeto.saraEmprega.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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

    // --- 1. CRIAÇÃO  ---

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CandidaturaResponseDTO> criar(
            @Valid @RequestBody CandidaturaRequestDTO dto,
            @AuthenticationPrincipal Jwt principal) { 
        
        UUID userIdLogado = UUID.fromString(principal.getClaimAsString("userId"));
        
        CandidaturaRequestDTO seguroDto = new CandidaturaRequestDTO(userIdLogado, dto.vagaId()); 

        CandidaturaResponseDTO novaCandidatura = candidaturaServicePort.criar(seguroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCandidatura);
    }

    // --- 2. DESISTÊNCIA --- 
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> desistir(
            @PathVariable("id") UUID candidaturaId,
            @AuthenticationPrincipal Jwt principal) {

        UUID userIdLogado = UUID.fromString(principal.getClaimAsString("userId"));
        
        candidaturaServicePort.desistir(candidaturaId, userIdLogado);
        
        return ResponseEntity.noContent().build(); 
    }

    // --- 2. BUSCAS (CANDIDATO e EMPRESA) ---

    @GetMapping("/minhas")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CandidaturaResponseDTO>> buscarMinhasCandidaturas(
            @AuthenticationPrincipal Jwt principal) {

        UUID userIdLogado = UUID.fromString(principal.getClaimAsString("userId"));
        List<CandidaturaResponseDTO> candidaturas = candidaturaServicePort.buscarPorUserId(userIdLogado);
        
        return ResponseEntity.ok(candidaturas);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'EMPRESA', 'ADMIN')") 
    public ResponseEntity<CandidaturaResponseDTO> buscarPorId(@PathVariable UUID id) {
        
        CandidaturaResponseDTO candidatura = candidaturaServicePort.buscarPorId(id);
        
        return ResponseEntity.ok(candidatura);
    }

    @GetMapping("/vaga/{vagaId}")
    @PreAuthorize("hasAnyRole('EMPRESA', 'ADMIN')")
    public ResponseEntity<List<CandidaturaResponseDTO>> buscarPorVaga(@PathVariable UUID vagaId) {
        
        List<CandidaturaResponseDTO> candidaturas = candidaturaServicePort.buscarPorVagaId(vagaId);
        
        return ResponseEntity.ok(candidaturas);
    }

    // --- 3. AÇÃO DA EMPRESA (Atualização de Status) ---

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('EMPRESA', 'ADMIN')")
    public ResponseEntity<CandidaturaResponseDTO> atualizarStatus(
            @PathVariable UUID id,
            @RequestParam StatusCandidatura status) {
        
        CandidaturaResponseDTO atualizada = candidaturaServicePort.atualizarStatus(id, status);
        
        return ResponseEntity.ok(atualizada);
    }
}