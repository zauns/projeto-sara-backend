package sara.projeto.saraEmprega.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
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

    @Test
    @DisplayName("A busca por ID de uma conta inexistente deve retornar uma exceção")
    void buscarPorIdComContaInexistente() {
        when(contaRepositoryPort.encontrarPorId(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> contaService.buscarPorId(id));

        verify(contaRepositoryPort).encontrarPorId(id);
    }

    @Test
    @DisplayName("A busca de todas as contas deve retornar uma lista com o DTO de todas as contas")
    void buscarTodasAsContasDeveRetornarUmaLista() {
        List<Conta> contas = List.of(conta, new ContaConcreta());
        when(contaRepositoryPort.encontrarTodas()).thenReturn(contas);

        List<ContaResponseDTO> resultado = contaService.buscarTodasAsContas();


        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(2, resultado.size());
        verify(contaRepositoryPort).encontrarTodas();
    }

    @Test
    @DisplayName("Ao excluir a conta, caso ela exista deve ser deletada")
    void excluirContaDeveDeletarContaExistente() {
        when(contaRepositoryPort.existePorId(id)).thenReturn(true);

        contaService.excluirConta(id);

        verify(contaRepositoryPort).existePorId(id);
        verify(contaRepositoryPort).deletar(id);
    }

    @Test
    @DisplayName("Ao tentar deletar uma conta que não existe, deve ser lançado uma exceção")
    void excluirContaInexistente() {
        when(contaRepositoryPort.existePorId(id)).thenReturn(false);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, 
            () -> contaService.excluirConta(id));
        
        Assertions.assertEquals("Conta não encontrada", exception.getMessage());
        verify(contaRepositoryPort).existePorId(id);
        verify(contaRepositoryPort, never()).deletar(id);
    }

    @Test
    @DisplayName("Ao buscar uma conta pelo e-mail, caso ela exista, deve ser retornado um Optional da conta")
    void buscarPorEmailDeveRetornarConta() {
        String email = "teste@example.com";
        when(contaRepositoryPort.encontrarPorEmail(email)).thenReturn(Optional.of(conta));

        Optional<Conta> resultado = contaService.buscarPorEmail(email);

        Assertions.assertTrue(resultado.isPresent());
        Assertions.assertEquals(conta, resultado.get());
        verify(contaRepositoryPort).encontrarPorEmail(email);
    }

    @Test
    @DisplayName("Ao buscar um e-mail de uma conta que não existe, deve ser retornado um optional vazio") //depois pode adptar o código para lançar uma exceção também
    void buscarPorEmailDeContaInexistente() {
        String email = "inexistente@example.com";
        when(contaRepositoryPort.encontrarPorEmail(email)).thenReturn(Optional.empty());

        Optional<Conta> resultado = contaService.buscarPorEmail(email);

        
        Assertions.assertTrue(resultado.isEmpty());
        verify(contaRepositoryPort).encontrarPorEmail(email);
    }
}
