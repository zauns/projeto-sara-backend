package sara.projeto.saraEmprega.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sara.projeto.saraEmprega.model.Conta;

/**
 * DTO base para resposta de operações com Conta.
 *
 * Esta classe serve como estrutura comum para todas as respostas de entidades
 * que estendem a classe Conta (User, Empresa, Secretaria, Administrador).
 *
 * COMO A ADAPTAÇÃO FUNCIONA:
 *
 * O construtor que recebe Conta usa reflection (getClass().getSimpleName())
 * para determinar dinamicamente o tipo da conta, permitindo que qualquer
 * subclasse de Conta seja automaticamente convertida.
 *
 * EXEMPLOS DE USO:
 *
 * 1. User user = userRepository.findById(id);
 *    ContaResponseDTO response = new ContaResponseDTO(user);
 *    // tipoConta = "User"
 *
 *
 * RESULTADO JSON:
 * {
 *   "id": "XXXXX",
 *   "nome": "João Silva",
 *   "email": "joao@email.com",
 *   "telefone": "99999999999",
 *   "endereco": "Rua, 123",
 *   "tipoConta": "User"
 * }
 *
 * EXTENSÕES ESPECÍFICAS:
 *
 * Para adicionar campos específicos de cada tipo de conta, crie DTOs especializados:
 * Exemplo: UserResponseDTO extends ContaResponseDTO
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaResponseDTO {

    private UUID id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private String tipoConta;

    public ContaResponseDTO(Conta conta) {
        this.id = conta.getId();
        this.nome = conta.getNome();
        this.email = conta.getEmail();
        this.telefone = conta.getTelefone();
        this.endereco = conta.getEndereco();
        this.tipoConta = conta.getClass().getSimpleName();
    }
}
