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
import sara.projeto.saraEmprega.dto.EmpresaRequestDTO;
import sara.projeto.saraEmprega.ports.EmpresaServicePort;
import sara.projeto.saraEmprega.service.EmpresaService;

@RestController
@RequestMapping("/empresa")
public class EmpresaController extends ContasController<EmpresaRequestDTO, EmpresaServicePort> {

    protected EmpresaController(EmpresaService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<ContaResponseDTO> criar(@Valid @RequestBody EmpresaRequestDTO dto) {
        ContaResponseDTO novaEmpresa = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEmpresa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody EmpresaRequestDTO dto) {
        ContaResponseDTO contaAtualizada = service.atualizar(id, dto);
        return ResponseEntity.ok(contaAtualizada);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @GetMapping("/pendentes")
    public ResponseEntity<List<ContaResponseDTO>> buscarPendentes() {
        return ResponseEntity.ok(service.getEmpresasNaoValidadas());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @PutMapping("/aprovar/{id}")
    public ResponseEntity<ContaResponseDTO> aprovarEmpresa(@PathVariable UUID id) {
        return ResponseEntity.ok(service.aprovarEmpresa(id));
    }

}
