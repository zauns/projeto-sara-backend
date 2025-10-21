package sara.projeto.saraEmprega.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SecretariaRequestDTO(
    @NotBlank(message = "Nome é obrigatório") String nome,
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    String email,
    @NotBlank(message = "Senha é obrigatória") String senha,
    @NotBlank(message = "Telefone é obrigatório") String telefone,
    @NotBlank(message = "Endereço é obrigatório") String endereco,
    @NotBlank(message = "Município é obrigatório") String municipio
) {}
