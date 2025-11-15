package sara.projeto.saraEmprega.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.SecretariaRequestDTO;
import sara.projeto.saraEmprega.ports.SecretariaServicePort;
import sara.projeto.saraEmprega.service.SecretariaService;

@RestController
@RequestMapping("/secretaria")
public class SecretariaController extends ContasController<SecretariaRequestDTO, SecretariaServicePort> {

    protected SecretariaController(SecretariaService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<ContaResponseDTO> criar(@Valid @RequestBody SecretariaRequestDTO dto) {
        ContaResponseDTO novaSecretaria = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaSecretaria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody SecretariaRequestDTO dto) {
        ContaResponseDTO contaAtualizada = service.atualizar(id, dto);
        return ResponseEntity.ok(contaAtualizada);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @GetMapping("/pendentes")
    public ResponseEntity<List<ContaResponseDTO>> buscarPendentes() {
        return ResponseEntity.ok(service.getSecretariasNaoValidadas());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @PutMapping("/aprovar/{id}")
    public ResponseEntity<ContaResponseDTO> aprovarSecretaria(@PathVariable UUID id) {
        return ResponseEntity.ok(service.aprovarSecretaria(id));
    }
}
