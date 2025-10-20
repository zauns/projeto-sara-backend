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
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("SECRETARIA")
public class Secretaria extends Conta {

    private String municipio;

    public void gerarRelatorio() {
        System.out.println("Teste: \n" + this.toString());
    }
}
