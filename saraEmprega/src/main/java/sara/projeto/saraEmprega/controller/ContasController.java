package sara.projeto.saraEmprega.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.ports.ContaServicePort;

/**
 * Controller base abstrato para operações comuns de Conta.
 *
 * Como usar:
 * 1. Estenda esta classe em seu controller específico (ex: UserController, EmpresaController)
 * 2. Defina os genéricos: <TRequestDTO, TService>
 *    - TRequestDTO: DTO para criação/atualização
 *    - TService: Serviço que implementa ContaServicePort
 * 3. Injete o serviço via construtor
 * 4. Implemente métodos específicos (criar, atualizar) no controller filho
 *
 * Exemplo:
 * @RestController
 * @RequestMapping("/user")
 * public class UserController extends ContasController<UserRequestDTO, UserServicePort> {
 *     public UserController(UserServicePort service) {
 *         super(service);
 *     }
 *
 *     @PostMapping
 *     public ResponseEntity<ContaResponseDTO> criar(@RequestBody UserRequestDTO dto) {
 *         // implementação específica
 *     }
 * }
 *
 * Endpoints herdados automaticamente:
 * - GET /{id} - Buscar conta por ID
 * - GET / - Listar todas as contas
 * - DELETE /{id} - Excluir conta
 */
public abstract class ContasController<TRequestDTO, TService extends ContaServicePort> {

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
