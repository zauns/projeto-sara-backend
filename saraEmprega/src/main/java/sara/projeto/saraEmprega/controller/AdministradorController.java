package sara.projeto.saraEmprega.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sara.projeto.saraEmprega.dto.AdministradorRequestDTO;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.ports.AdministradorServicePort;
import sara.projeto.saraEmprega.service.AdministradorService;

@RestController
@RequestMapping("/administrador")
public class AdministradorController extends ContasController<AdministradorRequestDTO, AdministradorServicePort> {

    protected AdministradorController(AdministradorService administradorService) {
        super(administradorService);
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ContaResponseDTO> criar(@Valid @RequestBody AdministradorRequestDTO dto) {
        ContaResponseDTO novaAdministracao = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAdministracao);
    }

    @GetMapping("/dados/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or (hasRole('ADMIN') and authentication.principal.claims['userId'] == #id.toString())")
    public ResponseEntity<AdministradorRequestDTO> getDados(@PathVariable UUID id) {
        AdministradorRequestDTO admin = service.getDados(id);
        return ResponseEntity.ok(admin);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ContaResponseDTO> atualizar(@PathVariable UUID id,
            @Valid @RequestBody AdministradorRequestDTO dto) {
        ContaResponseDTO novaAdministracao = service.atualizar(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(novaAdministracao);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or (hasRole('ADMIN') and authentication.principal.claims['userId'] == #id.toString())")
    public ResponseEntity<Void> excluirConta(@PathVariable UUID id) {
        service.excluirConta(id);
        return ResponseEntity.noContent().build();
    }
}
