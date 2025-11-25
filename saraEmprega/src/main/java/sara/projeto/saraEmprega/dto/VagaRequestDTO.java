package sara.projeto.saraEmprega.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import java.util.List;

public record VagaRequestDTO(    
    @NotBlank(message = "Título é obrigatório") String titulo,
    @NotBlank(message = "Descrição é obrigatória") String descricao,
    @NotNull(message = "O ID da Empresa é obrigatório") 
    UUID empresaId,
    List<String> tags
) {}
