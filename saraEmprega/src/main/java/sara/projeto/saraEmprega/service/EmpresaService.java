package sara.projeto.saraEmprega.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.EmpresaRequestDTO;
import sara.projeto.saraEmprega.model.Empresa;
import sara.projeto.saraEmprega.ports.ContaRepositoryPort;
import sara.projeto.saraEmprega.ports.EmpresaRepositoryPort;
import sara.projeto.saraEmprega.ports.EmpresaServicePort;

@Service
@RequiredArgsConstructor
public class EmpresaService extends ContaService<Empresa> implements EmpresaServicePort {

    private final EmpresaRepositoryPort repositorio;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected ContaRepositoryPort<Empresa> repositorio() {
        return this.repositorio;
    }

    @Override
    @Transactional
    public ContaResponseDTO criar(EmpresaRequestDTO dto) {
        Empresa empresa = new Empresa();
        mapear(dto, empresa);
        Empresa novaEmpresa = repositorio.salvar(empresa);
        return new ContaResponseDTO(novaEmpresa);
    }

    @Override
    @Transactional
    public ContaResponseDTO atualizar(UUID id, EmpresaRequestDTO dto) {
        Empresa empresa = repositorio.encontrarPorId(id)
            .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com o ID: " + id));
        mapear(dto, empresa);
        Empresa atualizada = repositorio.salvar(empresa);
        return new ContaResponseDTO(atualizada);
    }

    //utilizado em vagas
    @Transactional(readOnly = true)
    public Empresa buscarEmpresaPorId(UUID id) {
        Empresa empresa = repositorio.encontrarPorId(id)
            .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com o ID: " + id));
        return empresa;
    }

    private void mapear(EmpresaRequestDTO dto, Empresa empresa) {
        empresa.setNome(dto.nome());
        empresa.setEmail(dto.email());
        empresa.setTelefone(dto.telefone());
        empresa.setEndereco(dto.endereco());
        empresa.setSenhaHash(passwordEncoder.encode(dto.senha()));
        empresa.setCnpj(dto.cnpj());
        empresa.setBiografia(dto.biografia());
        empresa.setLinks(dto.links());
        empresa.setValidada(false);
    }

    @Transactional(readOnly = true)
    public List<ContaResponseDTO> getEmpresasNaoValidadas() {
        return repositorio.findByIsValidadaFalse().stream()
            .map(ContaResponseDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public ContaResponseDTO aprovarEmpresa(UUID id) {
        Empresa empresa = repositorio.encontrarPorId(id)
            .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada"));
        
        empresa.setValidada(true);
        repositorio.salvar(empresa);
        return new ContaResponseDTO(empresa);
    }

}
