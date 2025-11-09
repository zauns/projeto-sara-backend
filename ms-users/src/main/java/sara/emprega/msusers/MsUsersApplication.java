    package sara.emprega.msusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import sara.emprega.msusers.repository.UserRepository;

    @SpringBootApplication(scanBasePackages = "sara.emprega")
    @EnableScheduling
    @EnableFeignClients
public class MsUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsUsersApplication.class, args);
    }

}
