package sara.projeto.saraEmprega.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sara.projeto.saraEmprega.DTO.ContaResponseDTO;
import sara.projeto.saraEmprega.DTO.SecretariaRequestDTO;
import sara.projeto.saraEmprega.service.ContaService;

@RestController
@RequestMapping("/secretarias")
public class SecretariaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<ContaResponseDTO> criarSecretaria(
        @Valid @RequestBody SecretariaRequestDTO dto
    ) {
        ContaResponseDTO novaConta = contaService.criarConta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
    }
}
