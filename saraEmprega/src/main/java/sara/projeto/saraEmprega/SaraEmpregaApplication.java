package sara.projeto.saraEmprega;

import org.springframework.boot.SpringApplication;

//Desabilitei o SpringSecurity enquanto o login e logout ainda não foram implementados para fins de testes
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SaraEmpregaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaraEmpregaApplication.class, args);
    }
}
