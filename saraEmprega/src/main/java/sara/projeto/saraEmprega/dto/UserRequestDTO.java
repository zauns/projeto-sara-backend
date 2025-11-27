package sara.projeto.saraEmprega.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import sara.projeto.saraEmprega.model.User;

public record UserRequestDTO(
        @NotBlank(message = "O nome é obrigatório") String name,
        @Email(message = "E-mail inválido") @NotBlank(message = "O e-mail é obrigatório") String email,
        @NotBlank(message = "A senha é obrigatória") String password) {
            public static UserRequestDTO converter(User user){
            return new UserRequestDTO(
                    user.getNome(),
                    user.getEmail(),
                    null
                    );
            }
}
