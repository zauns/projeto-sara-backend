package sara.emprega.msusers.dto;

import java.util.UUID;

public record CurriculumDTO(
        UUID id,
        byte[] data
) {
}
