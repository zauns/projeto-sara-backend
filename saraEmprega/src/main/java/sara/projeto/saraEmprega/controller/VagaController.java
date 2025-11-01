package sara.projeto.saraEmprega.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<VagaResponseDTO> criarVaga(
        @Valid @RequestBody VagaRequestDTO dto
    ){
        VagaResponseDTO novaVaga = vagaService.criarVaga(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaVaga);
    }
    
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

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirVaga(@PathVariable UUID id){
        vagaService.excluirVaga(id);
        return ResponseEntity.noContent().build();
    }
}
