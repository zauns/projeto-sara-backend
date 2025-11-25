package sara.projeto.saraEmprega.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import sara.projeto.saraEmprega.dto.CandidaturaRequestDTO;
import sara.projeto.saraEmprega.dto.CandidaturaResponseDTO;
import sara.projeto.saraEmprega.enums.StatusCandidatura;
import sara.projeto.saraEmprega.exception.DuplicidadeCandidaturaException;
import sara.projeto.saraEmprega.exception.VagaInativaException;
import sara.projeto.saraEmprega.model.Candidatura;
import sara.projeto.saraEmprega.model.Empresa;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.model.Vaga;
import sara.projeto.saraEmprega.ports.CandidaturaRepositoryPort;
import sara.projeto.saraEmprega.ports.UserRepositoryPort;
import sara.projeto.saraEmprega.ports.VagaRepositoryPort;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // Permite stubbings no setUp, mas será removido nos métodos testados
public class CandidaturaServiceTest {

    // Dependências (Mocks)
    @Mock
    private CandidaturaRepositoryPort candidaturaRepositoryPort;
    @Mock
    private VagaRepositoryPort vagaRepositoryPort;
    @Mock
    private UserRepositoryPort userRepositoryPort;

    // Classe sob teste (InjectMocks)
    @InjectMocks
    private CandidaturaService candidaturaService;

    // Variáveis de Teste
    private UUID vagaId;
    private UUID userId;
    private UUID candidaturaId;
    private CandidaturaRequestDTO requestDTO;
    
    // Entidades Mocks (AGORA COMPLETAS)
    private Empresa empresaExistente;
    private Vaga vagaAtiva;
    private User userExistente;
    private Candidatura candidaturaExistente;

    @BeforeEach
    void setUp() {
        vagaId = UUID.randomUUID();
        userId = UUID.randomUUID();
        candidaturaId = UUID.randomUUID();
        
        requestDTO = new CandidaturaRequestDTO(vagaId, userId);
        
        // 1. MOCK da EMPRESA (Necessário para evitar NPE no VagaResponseDTO)
        empresaExistente = new Empresa();
        empresaExistente.setId(UUID.randomUUID());
        empresaExistente.setNome("Sara Emprega Tech");
        
        // 2. MOCK de Vaga Ativa (COMPLETA)
        vagaAtiva = new Vaga();
        vagaAtiva.setId(vagaId);
        vagaAtiva.setAtiva(true);
        vagaAtiva.setEmpresa(empresaExistente); // VINCULADO

        // 3. MOCK de User (COMPLETO)
        userExistente = new User();
        userExistente.setId(userId);
        userExistente.setNome("Candidato Teste");

        // 4. MOCK de Candidatura Existente (COMPLETA)
        candidaturaExistente = new Candidatura();
        candidaturaExistente.setId(candidaturaId);
        candidaturaExistente.setVaga(vagaAtiva);
        candidaturaExistente.setUser(userExistente);
        candidaturaExistente.setStatus(StatusCandidatura.PENDENTE);

        // REMOVIDO: Mocks globais de findById para evitar UnnecessaryStubbingException
    }

    // ------------------------------------------------------------------
    // TESTES DO MÉTODO CRIAR
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Criação: Deve criar candidatura com sucesso e status PENDENTE")
    void criarCandidatura_ComSucesso() {
        // Mocks necessários para este teste
        when(vagaRepositoryPort.findById(vagaId)).thenReturn(Optional.of(vagaAtiva));
        when(userRepositoryPort.getUserById(userId)).thenReturn(Optional.of(userExistente)); 
        when(candidaturaRepositoryPort.existsByVagaIdAndUserIdAndStatusNotIn(any(UUID.class), any(UUID.class), anyList())).thenReturn(false);

        // Mock do save
        when(candidaturaRepositoryPort.save(any(Candidatura.class)))
            .thenAnswer(invocation -> {
                Candidatura c = invocation.getArgument(0);
                c.setId(UUID.randomUUID()); 
                return c;
            });

        // Ação
        CandidaturaResponseDTO response = candidaturaService.criar(requestDTO);

        // Verificação
        assertNotNull(response);
        assertEquals(vagaId, response.getVaga().getId());
        assertEquals(userId, response.getUser().getId());
        assertEquals(StatusCandidatura.PENDENTE, response.getStatus());
        verify(candidaturaRepositoryPort, times(1)).save(any(Candidatura.class));
    }
    
    @Test
    @DisplayName("Criação: Deve lançar DuplicidadeCandidaturaException se já houver candidatura ativa")
    void criarCandidatura_DeveLancarExcecao_QuandoDuplicidade() {
        // Mocks necessários
        when(vagaRepositoryPort.findById(vagaId)).thenReturn(Optional.of(vagaAtiva));
        when(userRepositoryPort.getUserById(userId)).thenReturn(Optional.of(userExistente));
        when(candidaturaRepositoryPort.existsByVagaIdAndUserIdAndStatusNotIn(any(UUID.class), any(UUID.class), anyList())).thenReturn(true); 

        // Ação e Verificação
        assertThrows(DuplicidadeCandidaturaException.class, () -> {
            candidaturaService.criar(requestDTO);
        });
        
        verify(candidaturaRepositoryPort, times(0)).save(any(Candidatura.class));
    }

    @Test
    @DisplayName("Criação: Deve lançar VagaInativaException se a vaga não estiver ativa")
    void criarCandidatura_DeveLancarExcecao_QuandoVagaInativa() {
        // Cenário: Vaga inativa (Vaga incompleta, mas o DTO de resposta deve ser OK)
        Vaga vagaInativa = new Vaga();
        vagaInativa.setId(vagaId);
        vagaInativa.setAtiva(false);
        vagaInativa.setEmpresa(empresaExistente);
        
        // Mocks necessários
        when(vagaRepositoryPort.findById(vagaId)).thenReturn(Optional.of(vagaInativa));
        when(userRepositoryPort.getUserById(userId)).thenReturn(Optional.of(userExistente));

        // Ação e Verificação
        assertThrows(VagaInativaException.class, () -> {
            candidaturaService.criar(requestDTO);
        });
        
        verify(candidaturaRepositoryPort, times(0)).save(any(Candidatura.class));
    }
    
    @Test
    @DisplayName("Criação: Deve lançar exceção se Vaga ou User não existirem (IllegalArgumentException no mapear)")
    void criarCandidatura_DeveLancarExcecao_QuandoVagaOuUserNaoEncontrados() {
        // Mock: Vaga não encontrada
        when(vagaRepositoryPort.findById(vagaId)).thenReturn(Optional.empty()); 
        
        // Ação e Verificação
        assertThrows(IllegalArgumentException.class, () -> { 
            candidaturaService.criar(requestDTO);
        });
        
        verify(candidaturaRepositoryPort, times(0)).save(any(Candidatura.class));
    }

    // ------------------------------------------------------------------
    // TESTES DO MÉTODO DESISTIR
    // ------------------------------------------------------------------
    
    @Test
    @DisplayName("Desistir: Deve excluir a candidatura se for do dono e status PENDENTE")
    void desistirCandidatura_ComSucesso() {
        // Mock necessário
        when(candidaturaRepositoryPort.findById(candidaturaId)).thenReturn(Optional.of(candidaturaExistente));

        // Ação
        candidaturaService.desistir(candidaturaId, userId);

        // Verificação: Deve chamar o método de exclusão
        verify(candidaturaRepositoryPort, times(1)).delete(candidaturaId);
    }
    
    @Test
    @DisplayName("Desistir: Deve lançar exceção se status não for PENDENTE")
    void desistirCandidatura_StatusInvalido() {
        // Cenário: Status EM_ANALISE
        Candidatura candidaturaEmAnalise = new Candidatura();
        candidaturaEmAnalise.setId(candidaturaId);
        candidaturaEmAnalise.setUser(userExistente);
        candidaturaEmAnalise.setVaga(vagaAtiva);
        candidaturaEmAnalise.setStatus(StatusCandidatura.EM_ANALISE); 

        // Mock necessário
        when(candidaturaRepositoryPort.findById(candidaturaId)).thenReturn(Optional.of(candidaturaEmAnalise));

        // Ação e Verificação
        assertThrows(IllegalStateException.class, () -> {
            candidaturaService.desistir(candidaturaId, userId);
        });
        
        verify(candidaturaRepositoryPort, times(0)).delete(candidaturaId);
    }
    
    @Test
    @DisplayName("Desistir: Deve lançar exceção se o usuário logado não for o dono")
    void desistirCandidatura_UsuarioNaoDono() {
        UUID outroUserId = UUID.randomUUID();
        
        // Mock necessário
        when(candidaturaRepositoryPort.findById(candidaturaId)).thenReturn(Optional.of(candidaturaExistente));

        // Ação e Verificação: Tentativa de desistir com outro ID
        assertThrows(IllegalStateException.class, () -> {
            candidaturaService.desistir(candidaturaId, outroUserId);
        });
        
        verify(candidaturaRepositoryPort, times(0)).delete(candidaturaId);
    }
    
    @Test
    @DisplayName("Desistir: Deve lançar EntityNotFoundException se candidatura não for encontrada")
    void desistirCandidatura_NaoEncontrada() {
        // Mock necessário
        when(candidaturaRepositoryPort.findById(candidaturaId)).thenReturn(Optional.empty());

        // Ação e Verificação
        assertThrows(EntityNotFoundException.class, () -> {
            candidaturaService.desistir(candidaturaId, userId);
        });
        
        verify(candidaturaRepositoryPort, times(0)).delete(candidaturaId);
    }

    // ------------------------------------------------------------------
    // TESTES DO MÉTODO ATUALIZAR STATUS
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Atualizar Status: Deve mudar o status e salvar")
    void atualizarStatus_ComSucesso() {
        StatusCandidatura novoStatus = StatusCandidatura.EM_ANALISE;
        
        // Mock necessário
        when(candidaturaRepositoryPort.findById(candidaturaId)).thenReturn(Optional.of(candidaturaExistente));
        
        // Mock do save
        when(candidaturaRepositoryPort.save(any(Candidatura.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // Ação
        CandidaturaResponseDTO response = candidaturaService.atualizarStatus(candidaturaId, novoStatus);

        // Verificação
        assertEquals(novoStatus, response.getStatus());
        verify(candidaturaRepositoryPort, times(1)).save(any(Candidatura.class));
    }

    @Test
    @DisplayName("Atualizar Status: Deve lançar EntityNotFoundException se candidatura não for encontrada")
    void atualizarStatus_NaoEncontrada() {
        // Mock necessário
        when(candidaturaRepositoryPort.findById(candidaturaId)).thenReturn(Optional.empty());

        // Ação e Verificação
        assertThrows(EntityNotFoundException.class, () -> {
            candidaturaService.atualizarStatus(candidaturaId, StatusCandidatura.APROVADA);
        });
        
        verify(candidaturaRepositoryPort, times(0)).save(any(Candidatura.class));
    }
}