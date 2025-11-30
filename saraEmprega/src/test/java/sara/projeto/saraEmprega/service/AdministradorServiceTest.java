package sara.projeto.saraEmprega.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.doAnswer;

import sara.projeto.saraEmprega.dto.AdministradorRequestDTO;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.model.Administrador;
import sara.projeto.saraEmprega.ports.ContaRepositoryPort;
import sara.projeto.saraEmprega.util.Mapper;

@ExtendWith(MockitoExtension.class)
public class AdministradorServiceTest {

    @Mock // define uma dependência simulada, no caso as interfaces
    private ContaRepositoryPort<Administrador> repositorio; // trocar este tipo para uma porta definida

    @Mock
    private Mapper mapper;

    @InjectMocks // injeta a dependências simuladas acima no objeto abaixo
    private AdministradorService administradorService;

    private AdministradorRequestDTO requestDTO;
    private Administrador administrador;
    private UUID adminId;

    @BeforeEach // antes de cada teste, usa este setUp de dados
    void setUp() {
        adminId = UUID.randomUUID();
        requestDTO = new AdministradorRequestDTO(
                "Admin Teste",
                "amor@teste.com",
                "senha123",
                "123456789",
                "Rua Teste",
                false);
        administrador = new Administrador();
        administrador.setId(adminId);
        administrador.setNome("Admin Existente");
        administrador.setEmail("existente@teste.com");
    }

    @Test
    void criarAdministradorComSucesso() {

        Administrador adminMapeado = new Administrador();
        adminMapeado.setNome("Admin Teste");

        when(mapper.administradorParaEntidade(requestDTO)).thenReturn(adminMapeado);
        when(repositorio.salvar(any(Administrador.class))).thenAnswer(i -> i.getArgument(0));

        ContaResponseDTO responseDTO = administradorService.criar(requestDTO);

        Assertions.assertEquals("Admin Teste", responseDTO.getNome());
        verify(mapper).administradorParaEntidade(requestDTO);
        verify(repositorio).salvar(adminMapeado);
    }

    @Test
    void deveAtualizarAdministradorComSucesso() {
        //exemplo de como queremos os dados no final
        AdministradorRequestDTO dtoAtualizado = new AdministradorRequestDTO("Nome Atualizado", "amores2@email.com",
                "novaSenha", "9999", "Nova Rua", true);

        //simulação da atualização
        when(repositorio.encontrarPorId(adminId)).thenReturn(Optional.of(administrador));
        when(repositorio.salvar(any(Administrador.class))).thenAnswer(i -> i.getArgument(0));

        doAnswer(inv -> {
            Administrador a = inv.getArgument(1);
            a.setNome("Nome Atualizado");
            return null;
        }).when(mapper).atualizaAdministradorDeDTO(dtoAtualizado, administrador);

        ContaResponseDTO response = administradorService.atualizar(adminId, dtoAtualizado);

        //validação dos dados
        Assertions.assertEquals("Nome Atualizado", response.getNome());
        verify(mapper).atualizaAdministradorDeDTO(dtoAtualizado, administrador);
        verify(repositorio).salvar(administrador);
    }

}
