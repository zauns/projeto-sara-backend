package sara.projeto.saraEmprega;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SaraEmpregaApplication {

    public static void main(String[] args) {
        System.out.println("Yei Abriu!:");
        SpringApplication.run(SaraEmpregaApplication.class, args);
    }
}
