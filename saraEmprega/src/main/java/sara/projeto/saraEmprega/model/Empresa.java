package sara.projeto.saraEmprega.model;

import java.util.Set;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Empresa extends Conta{

    private String cnpj;
    private String biografia;
    private boolean isValidada = false;
    private String links;
    
    // Relacionamento One-to-Many com Vaga
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vaga> vagas; // vagas oferecidas pela empresa
    
}