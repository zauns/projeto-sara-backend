package sara.projeto.saraEmprega.service;

import java.util.List;
import java.util.UUID;
import java.util.Collections;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import sara.projeto.saraEmprega.dto.CandidaturaRequestDTO;
import sara.projeto.saraEmprega.dto.CandidaturaResponseDTO;
import sara.projeto.saraEmprega.enums.StatusCandidatura;
import sara.projeto.saraEmprega.exception.DuplicidadeCandidaturaException;
import sara.projeto.saraEmprega.exception.VagaInativaException;
import sara.projeto.saraEmprega.model.Candidatura;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.model.Vaga;
import sara.projeto.saraEmprega.ports.CandidaturaRepositoryPort;
import sara.projeto.saraEmprega.ports.CandidaturaServicePort;
import sara.projeto.saraEmprega.ports.UserRepositoryPort;
import sara.projeto.saraEmprega.ports.VagaRepositoryPort;

@Service
@RequiredArgsConstructor
public class CandidaturaService implements CandidaturaServicePort {

    // Ports
    private final CandidaturaRepositoryPort candidaturaRepositoryPort;
    private final VagaRepositoryPort vagaRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;



    // 1. CRIAR CANDIDATURA
    @Override
    @Transactional
    public CandidaturaResponseDTO criar(CandidaturaRequestDTO dto) {

        UUID userIdLogado = getUserIdLogado(); // Obtém o ID do Candidato logado

        List<StatusCandidatura> statusQuePermitemReaplicacao = Collections.singletonList(StatusCandidatura.REJEITADA);
        if (candidaturaRepositoryPort.existsByVagaIdAndUserIdAndStatusNotIn(
                dto.vagaId(),
                userIdLogado, // Usa o ID logado
                statusQuePermitemReaplicacao
            )) {
            throw new DuplicidadeCandidaturaException("Usuário já possui uma candidatura ativa para esta vaga.");
        }

        Candidatura candidatura = new Candidatura();
        
        // Mapeia usando o ID do usuário logado e o ID da vaga do DTO
        //REFATORAR CASO O PROJETO TENHA CONTINUACAO
        mapear(new CandidaturaRequestDTO(dto.vagaId(), userIdLogado), candidatura);

        Candidatura novaCandidatura = candidaturaRepositoryPort.save(candidatura);
        return new CandidaturaResponseDTO(novaCandidatura);
    }

    // 2. ATUALIZAR STATUS (Ação da Empresa)
    @Override
    public CandidaturaResponseDTO atualizarStatus(UUID id, StatusCandidatura status) {

        Candidatura candidatura = buscarCandidatura(id);

        verificarAutorizacaoEmpresa(candidatura.getVaga().getId());

        candidatura.setStatus(status);
        Candidatura candidaturaAtualizada = candidaturaRepositoryPort.save(candidatura);
        return new CandidaturaResponseDTO(candidaturaAtualizada);
    }

    // 3. DESISTIR DA CANDIDATURA (Ação do Candidato)
    @Override
    @Transactional
    public CandidaturaResponseDTO desistir(UUID candidaturaId) {

        Candidatura candidatura = buscarCandidatura(candidaturaId);

        verificarAutorizacaoCandidato(candidatura.getUser().getId());

        if (candidatura.getStatus() != StatusCandidatura.PENDENTE) {
             throw new IllegalStateException("Não é permitido desistir de candidaturas que não estejam em status PENDENTE. Status atual: " + candidatura.getStatus());
        }

        candidaturaRepositoryPort.delete(candidaturaId);
        return new CandidaturaResponseDTO(candidatura);
    }

    // 4. BUSCAR CANDIDATURA POR ID
    @Override
    @Transactional(readOnly = true)
    public CandidaturaResponseDTO buscarPorId(UUID id) {
        Candidatura candidatura = buscarCandidatura(id);

        // VERIFICAÇÃO DE AUTORIZAÇÃO: Permite Candidato dono, Empresa dona da vaga, ou Admin/SuperAdmin
        verificarAutorizacaoBusca(candidatura);

        return new CandidaturaResponseDTO(candidatura);
    }

    // 5. BUSCAR CANDIDATURAS DO USER LOGADO
    @Override
    @Transactional(readOnly = true)
    public List<CandidaturaResponseDTO> buscarMinhasCandidaturas() {

        UUID userIdLogado = getUserIdLogado(); // Obtém o ID do Candidato logado

        return candidaturaRepositoryPort.findByUserId(userIdLogado)
            .stream()
            .map(CandidaturaResponseDTO::new)
            .toList();
    }

    // 6. BUSCAR CANDIDATURAS POR VAGA ID (Ação da Empresa)
    @Override
    @Transactional(readOnly = true)
    public List<CandidaturaResponseDTO> buscarPorVagaId(UUID vagaId) {

        verificarAutorizacaoEmpresa(vagaId);

        return candidaturaRepositoryPort.findByVagaId(vagaId)
            .stream()
            .map(CandidaturaResponseDTO::new)
            .toList();
    }

    // 7. BUSCAR CANDIDATURAS POR USER ID
    @Override
    @Transactional(readOnly = true)
    public List<CandidaturaResponseDTO> buscarPorUserId(UUID userId) {
        if(!userRepositoryPort.existePorId(userId)) {
            throw new EntityNotFoundException("Usuário não encontrado com o ID: " + userId);
        }
        return candidaturaRepositoryPort.findByUserId(userId)
            .stream()
            .map(CandidaturaResponseDTO::new)
            .toList();
    }

    // 8. BUSCAR CANDIDATURAS POR STATUS
    @Override
    @Transactional(readOnly = true)
    public List<CandidaturaResponseDTO> buscarPorStatus(StatusCandidatura status) {
        return candidaturaRepositoryPort.findByStatus(status)
            .stream()
            .map(CandidaturaResponseDTO::new)
            .toList();
    }

    // --- MÉTODOS AUXILIARES ---

    private Candidatura buscarCandidatura(UUID id) {
        return candidaturaRepositoryPort.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Candidatura não encontrada com o ID: " + id));
    }

    private void mapear(CandidaturaRequestDTO dto, Candidatura candidatura) {
        Vaga vaga = vagaRepositoryPort.findById(dto.vagaId())
            .orElseThrow(() -> new IllegalArgumentException("Vaga não encontrada com o ID: " + dto.vagaId()));

        User user = userRepositoryPort.encontrarPorId(dto.userId())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + dto.userId()));

        if (!vaga.isAtiva()) {
            throw new VagaInativaException("A vaga não está ativa para candidaturas.");
        }
        candidatura.setVaga(vaga);
        candidatura.setUser(user);
        candidatura.setStatus(StatusCandidatura.PENDENTE);
    }

    //  Obtém o ID do usuário (Candidato ou Empresa) logado
    private UUID getUserIdLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof Jwt)) {
            throw new AccessDeniedException("Usuário não autenticado via token JWT válido.");
        }

        Jwt jwt = (Jwt) principal;
        String userIdString = jwt.getClaimAsString("userId");

        if (userIdString == null) {
             throw new AccessDeniedException("Token JWT não possui a claim 'userId' necessária.");
        }

        return UUID.fromString(userIdString);
    }

    //  Verifica se o usuário logado é SUPER_ADMIN ou ADMIN
    private boolean isGlobalAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority ->
                    grantedAuthority.getAuthority().equals("SUPER_ADMIN") ||
                    grantedAuthority.getAuthority().equals("ADMIN")
                );
    }

    //  Verifica a posse para ações de Candidato (Desistir)
    private void verificarAutorizacaoCandidato(UUID candidatoIdDono) {
        if (isGlobalAdmin()) {
            return;
        }

        UUID userIdLogado = getUserIdLogado();

        if (candidatoIdDono.equals(userIdLogado)) {
            return;
        }

        throw new AccessDeniedException("Acesso negado. A candidatura pertence a outro usuário.");
    }

    //  Verifica a posse para ações de Empresa (Atualizar Status, Buscar por Vaga)
    private void verificarAutorizacaoEmpresa(UUID vagaId) {
        if (isGlobalAdmin()) {
            return;
        }

        UUID empresaIdLogada = getUserIdLogado();

        Vaga vaga = vagaRepositoryPort.findById(vagaId)
            .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada."));

        if (vaga.getEmpresa().getId().equals(empresaIdLogada)) {
            return;
        }

        throw new AccessDeniedException("Acesso negado. Você não é o proprietário desta vaga.");
    }

    // Verifica a posse para busca por ID
    private void verificarAutorizacaoBusca(Candidatura candidatura) {
        if (isGlobalAdmin()) {
            return;
        }

        UUID userIdLogado = getUserIdLogado();

        if (candidatura.getUser().getId().equals(userIdLogado)) {
            return;
        }

        Vaga vaga = candidatura.getVaga();
        if (vaga.getEmpresa().getId().equals(userIdLogado)) {
            return;
        }

        throw new AccessDeniedException("Acesso negado. Você não tem permissão para visualizar esta candidatura.");
    }
}
