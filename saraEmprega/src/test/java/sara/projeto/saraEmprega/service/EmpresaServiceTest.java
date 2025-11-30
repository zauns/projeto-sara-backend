package sara.projeto.saraEmprega.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.doAnswer;

import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.EmpresaRequestDTO;
import sara.projeto.saraEmprega.model.Empresa;
import sara.projeto.saraEmprega.ports.EmpresaRepositoryPort;
import sara.projeto.saraEmprega.util.Mapper;

@ExtendWith(MockitoExtension.class)
public class EmpresaServiceTest {

    @Mock
    private EmpresaRepositoryPort repositorio;

    @Mock
    private Mapper mapper;

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

        Empresa empresaMapeada = new Empresa();
        empresaMapeada.setNome("Empresa legal");
        empresaMapeada.setEmail("empresa@muitolegal.com");
        empresaMapeada.setValidada(false);

        when(mapper.empresaParaEntidade(requestDTO)).thenReturn(empresaMapeada);
        when(repositorio.salvar(any(Empresa.class))).thenAnswer(i -> i.getArgument(0));

        ContaResponseDTO responseDTO = empresaService.criar(requestDTO);

        Assertions.assertEquals("Empresa legal", responseDTO.getNome());
        Assertions.assertEquals("Empresa", responseDTO.getTipoConta());

        verify(mapper).empresaParaEntidade(requestDTO);
        verify(repositorio).salvar(empresaMapeada);
    }

    @Test
    @DisplayName("A empresa deve ser atualizada sem problemas")
    void deveAtualizarEmpresaComSucesso() {
        EmpresaRequestDTO dtoAtualizado = new EmpresaRequestDTO(
                "Novo Nome",
                "novo@email.com",
                "senha",
                "123",
                "Rua",
                "CNPJ",
                "Bio",
                "Link");

        when(repositorio.encontrarPorId(empresaId)).thenReturn(Optional.of(empresa));
        when(repositorio.salvar(any(Empresa.class))).thenAnswer(i -> i.getArgument(0));

        doAnswer(invocation -> {
            Empresa e = invocation.getArgument(1);
            e.setNome("Novo Nome");
            return null;
        }).when(mapper).atualizaEmpresaDeDTO(dtoAtualizado, empresa);

        ContaResponseDTO responseDTO = empresaService.atualizar(empresaId, dtoAtualizado);

        Assertions.assertEquals("Novo Nome", responseDTO.getNome());
        verify(mapper).atualizaEmpresaDeDTO(dtoAtualizado, empresa);
        verify(repositorio).salvar(empresa);
    }

    @Test
    @DisplayName("Ao passar um id existente, o conteúdo retornado deve ser um ResponseDTO da empresa correspondente")
    void buscarEmpresaPorIdComSucesso() {

        when(repositorio.encontrarPorId(empresaId))
                .thenReturn(Optional.of(empresa));

        ContaResponseDTO resultado = empresaService.buscarPorId(empresaId);

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(empresaId, resultado.getId());
    }

}
