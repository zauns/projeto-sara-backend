package sara.projeto.saraEmprega.model;

<<<<<<< HEAD
import java.util.Set;

import jakarta.persistence.*;
import jakarta.persistence.DiscriminatorValue;
=======
>>>>>>> main
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa extends Conta{

    private String cnpj;
    private String biografia;
    private boolean isValidada;
    private String links;
    
    // Relacionamento One-to-Many com Vaga
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vaga> vagas; // vagas oferecidas pela empresa
    
}
