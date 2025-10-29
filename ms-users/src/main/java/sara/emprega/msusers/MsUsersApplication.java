    package sara.emprega.msusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sara.emprega.msusers.repository.UserRepository;

    @SpringBootApplication(scanBasePackages = "sara.emprega")
public class MsUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsUsersApplication.class, args);
    }

}
