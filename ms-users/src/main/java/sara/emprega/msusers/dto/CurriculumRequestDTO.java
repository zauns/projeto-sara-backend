package sara.emprega.msusers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CurriculumRequestDTO(
        @NotBlank(message = "O nome do arquivo é obrigatório")
        String fileName,

        @NotBlank(message = "O tipo do arquivo é obrigatório")
        String fileType,

        @NotNull(message = "O arquivo PDF é obrigatório")
        byte[] data
) {
}
