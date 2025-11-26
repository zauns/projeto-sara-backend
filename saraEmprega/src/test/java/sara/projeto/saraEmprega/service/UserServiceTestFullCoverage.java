package sara.projeto.saraEmprega.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.EntityNotFoundException;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.UserRequestDTO;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.ContaRepositoryPort;
import sara.projeto.saraEmprega.util.user_statagy.UpdateContext;

@ExtendWith(MockitoExtension.class)
public class UserServiceTestFullCoverage {

    @Mock
    private ContaRepositoryPort<User> repositorio;

    @Mock
    private UpdateContext updateContext;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRequestDTO requestDTO;
    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        requestDTO = new UserRequestDTO("User Teste", "user@teste.com", "senha123");

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

        when(passwordEncoder.encode("senha123")).thenReturn("hash123");
        when(repositorio.salvar(any(User.class))).thenAnswer(i -> i.getArgument(0));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        ContaResponseDTO response = userService.create(requestDTO);

        Assertions.assertEquals("User Teste", response.getNome());
        Assertions.assertEquals("user@teste.com", response.getEmail());

        verify(repositorio).salvar(captor.capture());
        User salvo = captor.getValue();

        Assertions.assertEquals("User Teste", salvo.getNome());
        Assertions.assertEquals("hash123", salvo.getSenhaHash());
        verifyNoMoreInteractions(repositorio);
    }

    @Test
    void create_deveFuncionarComSenhaEncoderRetornandoNull() {

        when(passwordEncoder.encode("senha123")).thenReturn(null);
        when(repositorio.salvar(any(User.class))).thenAnswer(i -> i.getArgument(0));

        UserRequestDTO dto = new UserRequestDTO("ABC", "a@a.com", "senha123");

        ContaResponseDTO res = userService.create(dto);

        Assertions.assertEquals("ABC", res.getNome());
        Assertions.assertEquals("a@a.com", res.getEmail());
    }

    @Test
    void create_deveAceitarRequestNuloSemExplodir() {

        Assertions.assertThrows(NullPointerException.class, () -> userService.create(null));
    }

    // --------------------------------------------------------------
    // UPDATE
    // --------------------------------------------------------------

    @Test
    void update_DeveAtualizarComSucesso() {

        UserRequestDTO dto = new UserRequestDTO("Novo", "novo@mail.com", "newpwd");

        when(repositorio.encontrarPorId(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newpwd")).thenReturn("newHash");
        when(repositorio.salvar(any(User.class))).thenAnswer(i -> i.getArgument(0));

        ContaResponseDTO res = userService.update(userId, dto);

        Assertions.assertEquals("Novo", res.getNome());
        Assertions.assertEquals("novo@mail.com", res.getEmail());
    }

    @Test
    void update_DeveLancarExcecao_QuandoNaoExistir() {

        when(repositorio.encontrarPorId(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.update(userId, requestDTO));

        verify(repositorio, never()).salvar(any());
    }

    @Test
    void update_ComSenhaEncoderNula() {

        UserRequestDTO dto = new UserRequestDTO("A", "b@c.com", "123");

        when(repositorio.encontrarPorId(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("123")).thenReturn(null);
        when(repositorio.salvar(any(User.class))).thenAnswer(i -> i.getArgument(0));

        ContaResponseDTO res = userService.update(userId, dto);

        Assertions.assertEquals("A", res.getNome());
        Assertions.assertEquals("b@c.com", res.getEmail());
        Assertions.assertNull(user.getSenhaHash());
    }

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
