package sara.projeto.saraEmprega.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CandidaturaRequestDTO(
    @NotNull(message = "O ID da vaga é obrigatório")
    UUID vagaId,
    @NotNull(message = "O ID do usuário é obrigatório")
    UUID userId
) {}
