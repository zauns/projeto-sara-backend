package sara.projeto.saraEmprega.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdministradorRequestDTO(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,
        @NotBlank(message = "Senha é obrigatória") String senha,
        @NotBlank(message = "Telefone é obrigatório") String telefone,
        @NotBlank(message = "Endereço é obrigatório") String endereco,
        @NotBlank(message = "O valor do campo é obrigatório") boolean isSuperAdmin) {
}
