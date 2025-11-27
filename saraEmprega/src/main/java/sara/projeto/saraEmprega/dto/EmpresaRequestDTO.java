package sara.projeto.saraEmprega.dto;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.br.CNPJ;
import sara.projeto.saraEmprega.model.Empresa;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmpresaRequestDTO(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,
        @NotBlank(message = "Senha é obrigatória") String senha,
        @NotBlank(message = "Telefone é obrigatório") String telefone,
        @NotBlank(message = "Endereço é obrigatório") String endereco,
        @NotBlank(message = "CNPJ é obrigatório") @CNPJ(message = "CNPJ em formato inválido") String cnpj,
        @NotBlank(message = "Biografia é obrigatória") String biografia,
        @URL(message = "Links em formato inválido") String links // pode ser opcional colocar
) {
    public static EmpresaRequestDTO converter(Empresa empresa){
        return new EmpresaRequestDTO(empresa.getNome(),
            empresa.getEmail(),
            null
            , empresa.getTelefone(),
            empresa.getEndereco(),
            empresa.getCnpj(),
            empresa.getBiografia(),
            empresa.getLinks()
        );
    }
}
