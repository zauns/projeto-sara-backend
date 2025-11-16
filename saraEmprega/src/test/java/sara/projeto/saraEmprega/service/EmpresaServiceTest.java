package sara.projeto.saraEmprega.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.foreign.Linker.Option;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.EmpresaRequestDTO;
import sara.projeto.saraEmprega.model.Empresa;
import sara.projeto.saraEmprega.ports.EmpresaRepositoryPort;

@ExtendWith(MockitoExtension.class)
public class EmpresaServiceTest {

    @Mock
    private EmpresaRepositoryPort repositorio;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmpresaService empresaService;

    private EmpresaRequestDTO requestDTO;
    private Empresa empresa;
    private UUID empresaId;

    @BeforeEach
    void setUp() {
        empresaId = UUID.randomUUID();
        requestDTO = new EmpresaRequestDTO(
                "Empresa legal",
                "empresa@muitolegal.com",
                "senha",
                "12345678",
                "Rua conhecida",
                "99.287.256/0001-99",
                "Uma empresa muito bacana e muito envolvente",
                "www.empresa.com");
        empresa = new Empresa();
        empresa.setId(empresaId);
        empresa.setNome("Uma empresa");
        empresa.setEmail("email@deumaempresa.com");
    }

    @Test
    @DisplayName("A empresa deve ser criada sem problemas com os campos formatados")
    void criarEmpresaComSucesso() {

        when(passwordEncoder.encode("senha"))
                .thenReturn("senha_hash");

        when(repositorio.salvar(any(Empresa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<Empresa> empresaCaptor = ArgumentCaptor.forClass(Empresa.class);

        ContaResponseDTO responseDTO = empresaService.criar(requestDTO);

        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals("Empresa legal", responseDTO.getNome());
        Assertions.assertEquals("empresa@muitolegal.com", responseDTO.getEmail());

        verify(repositorio, times(1)).salvar(empresaCaptor.capture());
        Empresa empresaSalva = empresaCaptor.getValue();

        Assertions.assertNotNull(empresaSalva);
        Assertions.assertEquals("Empresa legal", empresaSalva.getNome());
        Assertions.assertEquals("senha_hash", empresaSalva.getSenhaHash());
        Assertions.assertFalse(empresaSalva.isValidada());
    }

    @Test
    @DisplayName("A empresa deve ser atualizada sem problemas")
    void deveAtualizarEmpresaComSucesso() {
        EmpresaRequestDTO dtoAtualizado = new EmpresaRequestDTO(
                "novo nome de empresa",
                "novo@email.com",
                "nova senha",
                "12312312",
                "Nova rua",
                "10.791.910/0001-93",
                "Uma nova biografia para uma nova filosofia",
                "www.novolink.com");

        when(repositorio.encontrarPorId(empresaId))
                .thenReturn(Optional.of(empresa));

        when(passwordEncoder.encode("nova senha"))
                .thenReturn("novo_hash");

        when(repositorio.salvar(any(Empresa.class)))
                .then(invocation -> invocation.getArgument(0));

        ArgumentCaptor<Empresa> empresaCaptor = ArgumentCaptor.forClass(Empresa.class);
        ContaResponseDTO responseDTO = empresaService.atualizar(empresaId, dtoAtualizado);
        //código repetido da pra melhorar
        Assertions.assertNotNull(dtoAtualizado);
        Assertions.assertEquals("novo nome de empresa", dtoAtualizado.nome());
        Assertions.assertEquals("novo@email.com", dtoAtualizado.email());
        Assertions.assertEquals("12312312", dtoAtualizado.telefone());
        Assertions.assertEquals("Nova rua", dtoAtualizado.endereco());
        Assertions.assertEquals("10.791.910/0001-93", dtoAtualizado.cnpj());
        Assertions.assertEquals("Uma nova biografia para uma nova filosofia", dtoAtualizado.biografia());
        Assertions.assertEquals("www.novolink.com", dtoAtualizado.links());

        verify(repositorio, times(1)).salvar(empresaCaptor.capture());
        Empresa empresaAtualizada = empresaCaptor.getValue();

        Assertions.assertEquals("novo nome de empresa", empresaAtualizada.getNome());
        Assertions.assertEquals("novo@email.com", empresaAtualizada.getEmail());
        Assertions.assertEquals("12312312", empresaAtualizada.getTelefone());
        Assertions.assertEquals("Nova rua", empresaAtualizada.getEndereco());
        Assertions.assertEquals("10.791.910/0001-93", empresaAtualizada.getCnpj());
        Assertions.assertEquals("Uma nova biografia para uma nova filosofia", empresaAtualizada.getBiografia());
        Assertions.assertEquals("www.novolink.com", empresaAtualizada.getLinks());
    }

    @Test
    @DisplayName("Ao passar um id existente, o conteúdo retornado deve ser um ResponseDTO da empresa correspondente")
    void buscarEmpresaPorIdComSucesso(){

        when(repositorio.encontrarPorId(empresaId))
        .thenReturn(Optional.of(empresa));

        ContaResponseDTO resultado = empresaService.buscarPorId(empresaId);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(empresaId, resultado.getId());
    }

}