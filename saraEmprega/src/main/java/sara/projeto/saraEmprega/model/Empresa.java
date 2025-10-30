package sara.projeto.saraEmprega.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
    private boolean isAprovada;
    private boolean isValidada;
    private String links;
    
}
