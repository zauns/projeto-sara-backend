package sara.projeto.saraEmprega.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OlaMundo {

    @GetMapping("/")
    public String olaMundo() {
        return "Olá Mundo!";
    }
}
