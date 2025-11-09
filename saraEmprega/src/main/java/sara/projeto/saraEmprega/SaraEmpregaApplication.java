package sara.projeto.saraEmprega;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SaraEmpregaApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load(); //Lê os dados do arquivo .env da raiz do projeto
        dotenv
            .entries()
            .forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
            });

        SpringApplication app = new SpringApplication(
            SaraEmpregaApplication.class
        );
        app.setWebApplicationType(WebApplicationType.SERVLET);
        app.run(args);
    }
}
