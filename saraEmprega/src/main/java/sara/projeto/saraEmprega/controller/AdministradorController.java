package sara.projeto.saraEmprega.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import sara.projeto.saraEmprega.dto.AdministradorRequestDTO;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.service.AdministradorService;

public class AdministradorController extends ContasController<AdministradorRequestDTO, AdministradorService> {

    @Autowired
    private AdministradorService administradorService;

    protected AdministradorController(AdministradorService administradorService) {
        super(administradorService);
    }

    @PostMapping
    public ResponseEntity<ContaResponseDTO> criar(@Valid @RequestBody AdministradorRequestDTO dto) {
        ContaResponseDTO novaAdministracao = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAdministracao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> atualizar(@PathVariable UUID id,@Valid @RequestBody AdministradorRequestDTO dto) {
        ContaResponseDTO novaAdministracao = service.atualizar(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(novaAdministracao);
    }
}
