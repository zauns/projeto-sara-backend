package sara.projeto.saraEmprega.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.ports.ContaServicePort;


public abstract class ContasController<TRequestDTO, TService extends ContaServicePort> {

    @Autowired
    protected final TService service;

    protected ContasController(TService service){
        this.service = service;
    }

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

}
