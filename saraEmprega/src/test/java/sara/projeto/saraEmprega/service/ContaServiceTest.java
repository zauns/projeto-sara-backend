package sara.projeto.saraEmprega.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.model.Conta;
import sara.projeto.saraEmprega.ports.ContaRepositoryPort;
import sara.projeto.saraEmprega.testUtils.ContaConcreta;
import sara.projeto.saraEmprega.testUtils.ContaServiceConcreta;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {

    @Mock
    private ContaRepositoryPort<Conta> contaRepositoryPort;

    private ContaService<Conta> contaService;

    private UUID id;
    private Conta conta;
    
    @BeforeEach
    void setUp() {
        contaService = new ContaServiceConcreta(contaRepositoryPort);
        id = UUID.randomUUID();
        conta = new ContaConcreta(
            id,
            "Beltrano",
            "beltrano@hotmail.com",
            "senha_hash",
            "Rua do lado",
            "telefone");
    }
    

    @Test
    @DisplayName("A busca por ID deve retornar o ContaResponseDTO corretamente quando existir")
    void buscaPorIdRetornarOContaResponseDTO() {
        
        when(contaRepositoryPort.encontrarPorId(id))
        .thenReturn(Optional.of(conta));

        ContaResponseDTO resultado = contaService.buscarPorId(id);

        Assertions.assertNotNull(resultado);
        verify(contaRepositoryPort).encontrarPorId(id);
    }

    

}
