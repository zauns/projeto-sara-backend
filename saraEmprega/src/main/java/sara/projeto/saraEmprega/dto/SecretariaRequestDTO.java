package sara.projeto.saraEmprega.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import sara.projeto.saraEmprega.model.Secretaria;

public record SecretariaRequestDTO(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,
        String senha,
        @NotBlank(message = "Telefone é obrigatório") String telefone,
        @NotBlank(message = "Endereço é obrigatório") String endereco,
        @NotBlank(message = "Município é obrigatório") String municipio) {
    public static SecretariaRequestDTO converter(Secretaria secretaria) {
        return new SecretariaRequestDTO(
            secretaria.getNome(),
            secretaria.getEmail(),
            null,
            secretaria.getTelefone(),
            secretaria.getEndereco(),
            secretaria.getMunicipio()
        );
    }
}
