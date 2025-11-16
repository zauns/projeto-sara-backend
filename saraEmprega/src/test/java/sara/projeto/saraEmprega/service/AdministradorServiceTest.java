package sara.projeto.saraEmprega.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import sara.projeto.saraEmprega.dto.AdministradorRequestDTO;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.model.Administrador;
import sara.projeto.saraEmprega.ports.ContaRepositoryPort;

@ExtendWith(MockitoExtension.class)
public class AdministradorServiceTest {

    @Mock//define uma dependência simulada, no caso as interfaces
    private ContaRepositoryPort<Administrador> repositorio;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks //injeta a dependências simuladas acima no objeto abaixo
    private AdministradorService administradorService;


    private AdministradorRequestDTO requestDTO;
    private Administrador administrador;
    private UUID adminId;

    @BeforeEach //antes de cada teste, usa este setUp de dados
    void setUp(){
        adminId = UUID.randomUUID();
        requestDTO = new AdministradorRequestDTO(
                "Admin Teste",
                "amor@teste.com",
                "senha123",
                "123456789",
                "Rua Teste",
                false
        );
        administrador = new Administrador();
        administrador.setId(adminId);
        administrador.setNome("Admin Existente");
        administrador.setEmail("existente@teste.com");
    }

    @Test
    void criarAdministradorComSucesso(){

        when(passwordEncoder.encode("senha123")) //quando isso acontecer
        .thenReturn("hash_com_sucesso"); //retorne isso se deu certo

        when(repositorio.salvar(any(Administrador.class)))
        .thenAnswer(invocation -> invocation.getArgument(0)); //quando esse método for chamado, pegue o argumento(o admin cadastrado) 
        //a Answer capturada é usada no verify lá embaixo, retornando quantas vezes o método foi feito

        //feito para capturar o endereço do objeto criado
        ArgumentCaptor<Administrador> adminCaptor = ArgumentCaptor.forClass(Administrador.class);

        ContaResponseDTO responseDTO = administradorService.criar(requestDTO);

        Assertions.assertNotNull(responseDTO); //assegure que este campo não é Null
        Assertions.assertEquals("Admin Teste", responseDTO.getNome()); //assegure que este campo é igual ao primeiro argumento
        Assertions.assertEquals("amor@teste.com", responseDTO.getEmail());

        verify(repositorio, times(1)).salvar(adminCaptor.capture()); //captura o admin gerado
        Administrador adminSalvo = adminCaptor.getValue();

        Assertions.assertNotNull(adminSalvo);
        Assertions.assertEquals("Admin Teste", adminSalvo.getNome());
        Assertions.assertEquals("hash_com_sucesso", adminSalvo.getSenhaHash()); // verifica se o hash da senha foi feito
        Assertions.assertFalse(adminSalvo.isSuperAdmin());
    }

    @Test
    void deveAtualizarAdministradorComSucesso() {
        //exemplo de como queremos os dados no final
        AdministradorRequestDTO dtoAtualizado = new AdministradorRequestDTO(
                "Nome Atualizado", "amores2@email.com", "novaSenha", 
                "9999", "Nova Rua", true
        );

        //simulação da atualização

        when(repositorio.encontrarPorId(adminId))
        .thenReturn(Optional.of(administrador));
        
        when(passwordEncoder.encode("novaSenha"))
        .thenReturn("nova_senha_hash");

        when(repositorio.salvar(any(Administrador.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
        
        //validação dos dados
        ArgumentCaptor<Administrador> adminCaptor = ArgumentCaptor.forClass(Administrador.class);
        ContaResponseDTO response = administradorService.atualizar(adminId, dtoAtualizado);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("Nome Atualizado", response.getNome());
        Assertions.assertEquals("amores2@email.com", response.getEmail());

        verify(repositorio, times(1)).salvar(adminCaptor.capture());
        Administrador adminAtualizado = adminCaptor.getValue();

        Assertions.assertEquals("Nome Atualizado", adminAtualizado.getNome());
        Assertions.assertEquals("nova_senha_hash", adminAtualizado.getSenhaHash());
        Assertions.assertTrue(adminAtualizado.isSuperAdmin());
        Assertions.assertEquals(adminId, adminAtualizado.getId());
    }

}
