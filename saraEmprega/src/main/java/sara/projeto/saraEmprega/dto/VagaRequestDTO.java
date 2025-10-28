package sara.projeto.saraEmprega.dto;
import jakarta.validation.constraints.NotBlank;
public record VagaRequestDTO(    
    @NotBlank(message = "Título é obrigatório") String titulo,
    @NotBlank(message = "Descrição é obrigatória") String descricao
) {}
