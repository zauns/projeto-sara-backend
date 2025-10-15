package sara.projeto.saraEmprega;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sara.projeto.saraEmprega.controller.OlaMundo;

@SpringBootApplication
public class SaraEmpregaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaraEmpregaApplication.class, args);
        OlaMundo olaMundo = new OlaMundo();
    }
}
