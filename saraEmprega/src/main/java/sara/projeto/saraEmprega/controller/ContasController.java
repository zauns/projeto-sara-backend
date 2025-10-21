package sara.projeto.saraEmprega.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.SecretariaRequestDTO;
import sara.projeto.saraEmprega.service.ContaService;

@RestController
@RequestMapping("/contas")
public class ContasController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/secretaria")
    public ResponseEntity<ContaResponseDTO> criarSecretaria(
        @Valid @RequestBody SecretariaRequestDTO dto
    ) {
        ContaResponseDTO novaConta = contaService.criarConta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> buscaContaPorID(
        @PathVariable UUID id
    ) {
        ContaResponseDTO conta = contaService.buscarContaPorId(id);
        return ResponseEntity.ok(conta);
    }

    @GetMapping
    public ResponseEntity<List<ContaResponseDTO>> buscarTodasAsContas() {
        List<ContaResponseDTO> todasAsContas =
            contaService.buscarTodasAsContas();
        return ResponseEntity.ok(todasAsContas);
    }

    @PutMapping("/secretaria/{id}")
    public ResponseEntity<ContaResponseDTO> atualizarSecretaria(
        @PathVariable UUID id,
        @Valid @RequestBody SecretariaRequestDTO dto
    ) {
        ContaResponseDTO contaAtualizada = contaService.atualizarSecretaria(
            id,
            dto
        );
        return ResponseEntity.ok(contaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirConta(@PathVariable UUID id) {
        contaService.excluirConta(id);
        return ResponseEntity.noContent().build();
    }
}
