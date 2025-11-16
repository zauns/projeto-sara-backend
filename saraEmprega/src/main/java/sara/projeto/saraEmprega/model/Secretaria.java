package sara.projeto.saraEmprega.model;

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
public class Secretaria extends Conta {

    private String municipio;
    private boolean isValidada = false;

    public void gerarRelatorio() {
        System.out.println("Teste: \n" + this.toString());
    }
}
