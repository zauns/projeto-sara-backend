package sara.projeto.saraEmprega.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmpresaRequestDTO (
    @NotBlank(message = "Nome é obrigatório") String nome,
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    String email,
    @NotBlank(message = "Senha é obrigatória") String senha,
    @NotBlank(message = "Telefone é obrigatório") String telefone,
    @NotBlank(message = "Endereço é obrigatório") String endereco,
    @NotBlank(message = "CNPJ é obrigatório") String cnpj,
    @NotBlank(message = "Biografia é obrigatória") String biografia,
    @NotBlank(message = "Links são obrigatórios") String links,
    @NotBlank(message = "CCMEI é obrigatório") String ccmei
) {}