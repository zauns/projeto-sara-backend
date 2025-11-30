package sara.projeto.saraEmprega.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.UserRequestDTO;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.ContaRepositoryPort;
import sara.projeto.saraEmprega.util.Mapper;
import sara.projeto.saraEmprega.util.user_statagy.UpdateContext;

@ExtendWith(MockitoExtension.class)
public class UserServiceTestFullCoverage {

    @Mock
    private ContaRepositoryPort<User> repositorio;

    @Mock
    private UpdateContext updateContext;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private UserService userService;

    private UserRequestDTO requestDTO;
    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        requestDTO = new UserRequestDTO("User Teste", "user@teste.com", "senha123", "1239123123", "Um endereço gentil");

        user = new User();
        user.setId(userId);
        user.setNome("Antigo");
        user.setEmail("old@mail.com");
        user.setSenhaHash("old_hash");
    }

    // --------------------------------------------------------------
    // CREATE
    // --------------------------------------------------------------

    @Test
    void create_DeveCriarComSucesso() {
        User userMapeado = new User();
        userMapeado.setNome("User Teste");
        userMapeado.setEmail("user@teste.com");

        when(mapper.userParaEntidade(requestDTO)).thenReturn(userMapeado);
        when(repositorio.salvar(any(User.class))).thenAnswer(i -> i.getArgument(0));

        ContaResponseDTO response = userService.create(requestDTO);

        Assertions.assertEquals("User Teste", response.getNome());
        verify(mapper).userParaEntidade(requestDTO); // Verifica se o mapper foi chamado
        verify(repositorio).salvar(userMapeado);
    }

    // @Test teste de senha agora no MapperTest
    // void create_deveFuncionarComSenhaEncoderRetornandoNull() {

    //     when(passwordEncoder.encode("senha123")).thenReturn(null);
    //     when(repositorio.salvar(any(User.class))).thenAnswer(i -> i.getArgument(0));

    //     UserRequestDTO dto = new UserRequestDTO("ABC", "a@a.com", "senha123", "123213123", "Um endereço ainda mais gentil");

    //     ContaResponseDTO res = userService.create(dto);

    //     Assertions.assertEquals("ABC", res.getNome());
    //     Assertions.assertEquals("a@a.com", res.getEmail());
    // }

    @Test
    void create_deveAceitarRequestNuloSemExplodir() {

        Assertions.assertThrows(NullPointerException.class, () -> userService.create(null));
    }

    // --------------------------------------------------------------
    // UPDATE
    // --------------------------------------------------------------

    @Test
    void update_DeveAtualizarComSucesso() {
        UserRequestDTO dto = new UserRequestDTO("Novo", "novo@mail.com", "newpwd", "123123563", "um lugar insano");

        when(repositorio.encontrarPorId(userId)).thenReturn(Optional.of(user));

        doAnswer(invocation -> {
            User u = invocation.getArgument(1);
            u.setNome("Novo");
            u.setEmail("novo@mail.com");
            return null;
        }).when(mapper).atualizaUserDeDTO(dto, user);

        when(repositorio.salvar(any(User.class))).thenAnswer(i -> i.getArgument(0));

        ContaResponseDTO res = userService.update(userId, dto);

        Assertions.assertEquals("Novo", res.getNome());
        Assertions.assertEquals("novo@mail.com", res.getEmail());

        verify(mapper).atualizaUserDeDTO(dto, user);
        verify(repositorio).salvar(user);
    }

    @Test
    void update_DeveLancarExcecao_QuandoNaoExistir() {

        when(repositorio.encontrarPorId(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.update(userId, requestDTO));

        verify(repositorio, never()).salvar(any());
        verifyNoInteractions(mapper);
    }

    // @Test teste com senha agora no MapperTest
    // void update_ComSenhaEncoderNula() {

    //     UserRequestDTO dto = new UserRequestDTO("A", "b@c.com", "123", "3812983912" , "um lugar peculiar");

    //     when(repositorio.encontrarPorId(userId)).thenReturn(Optional.of(user));
    //     when(passwordEncoder.encode("123")).thenReturn(null);
    //     when(repositorio.salvar(any(User.class))).thenAnswer(i -> i.getArgument(0));

    //     ContaResponseDTO res = userService.update(userId, dto);

    //     Assertions.assertEquals("A", res.getNome());
    //     Assertions.assertEquals("b@c.com", res.getEmail());
    //     Assertions.assertNull(user.getSenhaHash());
    // }

    @Test
    void update_RequestNull() {

        when(repositorio.encontrarPorId(userId)).thenReturn(Optional.of(user));

        Assertions.assertThrows(NullPointerException.class,
                () -> userService.update(userId, null));
    }

    // --------------------------------------------------------------
    // getUserByMail
    // --------------------------------------------------------------

    @Test
    void getUserByMail_DeveRetornarUsuario() {

        when(repositorio.encontrarPorEmail("user@teste.com"))
                .thenReturn(Optional.of(user));

        User encontrado = userService.getUserByMail("user@teste.com");

        Assertions.assertEquals("Antigo", encontrado.getNome());
    }

    @Test
    void getUserByMail_DeveLancarErro_QuandoNaoEncontrado() {

        when(repositorio.encontrarPorEmail("x@x.com"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class,
                () -> userService.getUserByMail("x@x.com"));
    }

    // --------------------------------------------------------------
    // curriculumUpdate
    // --------------------------------------------------------------

    @Test
    void curriculumUpdate_deveSalvar() {

        when(repositorio.salvar(any(User.class))).thenAnswer(i -> i.getArgument(0));

        user.setNome("NovoCurriculo");

        User atualizado = userService.curriculumUpdate(user);

        Assertions.assertEquals("NovoCurriculo", atualizado.getNome());
    }

    // --------------------------------------------------------------
    // Testes gerais de comportamento
    // --------------------------------------------------------------

    @Test
    void nenhumMetodoDoRepositórioChamadoSemMotivo() {
        verifyNoInteractions(repositorio);
    }

}
