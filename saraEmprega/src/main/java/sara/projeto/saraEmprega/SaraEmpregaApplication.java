package sara.projeto.saraEmprega;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SaraEmpregaApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SaraEmpregaApplication.class);
        app.setWebApplicationType(WebApplicationType.SERVLET);
        app.run(args);
    }
}
