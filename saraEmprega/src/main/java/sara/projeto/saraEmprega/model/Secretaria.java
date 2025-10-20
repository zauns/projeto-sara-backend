package sara.projeto.saraEmprega.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("SECRETARIA")
public class Secretaria extends Conta {

    public void gerarRelatorio() {
        System.out.println("Teste: \n" + this.toString());
    }
}
