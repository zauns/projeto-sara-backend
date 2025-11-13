package sara.projeto.saraEmprega.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController { //faz um teste rápido da interação entre o banco de dados e o backend
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
