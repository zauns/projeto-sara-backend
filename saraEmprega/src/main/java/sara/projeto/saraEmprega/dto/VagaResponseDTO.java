package sara.projeto.saraEmprega.dto;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sara.projeto.saraEmprega.model.Vaga;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VagaResponseDTO {
    
    private UUID id;
    private String titulo;
    private String descricao;
    private boolean isAtiva;
    private EmpresaResponseDTO empresa;
    private List<String> tags;

    public VagaResponseDTO(Vaga vaga) {
        this.id = vaga.getId();
        this.titulo = vaga.getTitulo();
        this.descricao = vaga.getDescricao();
        this.isAtiva = vaga.isAtiva();
        this.empresa = new EmpresaResponseDTO(vaga.getEmpresa());
        this.tags = vaga.getTags();
    }
}
