package sara.emprega.msusers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import sara.emprega.msusers.model.Curriculum;

public record UserRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String name,

        @Email(message = "E-mail inválido")
        @NotBlank(message = "O e-mail é obrigatório")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        String password,

        CurriculumRequestDTO curriculum
) {}