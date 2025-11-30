package sara.projeto.saraEmprega.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import sara.projeto.saraEmprega.model.User;

public record UserRequestDTO(
        @NotBlank(message = "O nome é obrigatório") String name,
        @Email(message = "E-mail inválido") @NotBlank(message = "O e-mail é obrigatório") String email,
        String password,
        @NotBlank(message = "Telefone é obrigatório") String telefone,
        @NotBlank(message = "Endereço é obrigatório") String endereco
        ){
            public static UserRequestDTO converter(User user){
            return new UserRequestDTO(
                    user.getNome(),
                    user.getEmail(),
                    null,
                    user.getTelefone(),
                    user.getEndereco()
                    );
            }
}
