package sara.projeto.saraEmprega.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.service.ContaService;


public abstract class ContasController<TRequestDTO, TService extends ContaService<?>> {

    @Autowired
    protected final TService service;

    protected ContasController(TService service){
        this.service = service;
    }

    // @PostMapping("/secretaria")
    // public ResponseEntity<ContaResponseDTO> criarSecretaria(
    // @Valid @RequestBody SecretariaRequestDTO dto
    // ) {
    // ContaResponseDTO novaConta = contaService.criarConta(dto);
    // return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
    // }

    // @PostMapping("/empresa")
    // public ResponseEntity<ContaResponseDTO> criarEmpresa(
    // @Valid @RequestBody EmpresaRequestDTO dto
    // ) {
    // ContaResponseDTO novaConta = contaService.criarConta(dto);
    // return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> buscaContaPorID(@PathVariable UUID id) {
        ContaResponseDTO conta = service.buscarPorId(id);
        return ResponseEntity.ok(conta);
    }

    @GetMapping
    public ResponseEntity<List<ContaResponseDTO>> buscarTodasAsContas() {
        List<ContaResponseDTO> todasAsContas = service.buscarTodasAsContas();
        return ResponseEntity.ok(todasAsContas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirConta(@PathVariable UUID id) {
        service.excluirConta(id);
        return ResponseEntity.noContent().build();
    }

    // @PutMapping("/secretaria/{id}")
    // public ResponseEntity<ContaResponseDTO> atualizarSecretaria(
    // @PathVariable UUID id,
    // @Valid @RequestBody SecretariaRequestDTO dto) {
    // ContaResponseDTO contaAtualizada = contaService.atualizarSecretaria(
    // id,
    // dto);
    // return ResponseEntity.ok(contaAtualizada);
    // }

    // @PutMapping("/empresa/{id}")
    // public ResponseEntity<ContaResponseDTO> atualizarEmpresa(
    // @PathVariable UUID id,
    // @Valid @RequestBody EmpresaRequestDTO dto) {
    // ContaResponseDTO contaAtualizada = contaService.atualizarEmpresa(
    // id,
    // dto);
    // return ResponseEntity.ok(contaAtualizada);
    // }
}
