package sara.projeto.saraEmprega.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record SecretariaResponseDTO(
    @NotBlank(message = "ID não pode ser vazio") UUID id,
    @NotBlank(message = "Nome não pode ser vazio") String nome,
    @NotBlank(message = "Email não pode ser vazio")
    @Email(message = "Email inválido")
    String email,
    @NotBlank(message = "Telefone não pode ser vazio") String telefone,
    @NotBlank(message = "Endereço não pode ser vazio") String endereco
) {}
