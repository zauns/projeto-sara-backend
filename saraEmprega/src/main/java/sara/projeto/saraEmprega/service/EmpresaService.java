package sara.projeto.saraEmprega.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.EmpresaRequestDTO;
import sara.projeto.saraEmprega.dto.EmpresaResponseDTO;
import sara.projeto.saraEmprega.model.Empresa;
import sara.projeto.saraEmprega.repository.EmpresaRepository;

@Service
public class EmpresaService extends ContaService<Empresa> {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    protected JpaRepository<Empresa, UUID> repositorio() {
        return empresaRepository;
    }

    @Transactional
    public ContaResponseDTO criar(EmpresaRequestDTO dto) {
        Empresa empresa = new Empresa();
        mapToEmpresa(dto, empresa);
        Empresa novaEmpresa = empresaRepository.save(empresa);
        return new ContaResponseDTO(novaEmpresa);
    }

    @Transactional
    public ContaResponseDTO atualizar(UUID id, EmpresaRequestDTO dto) {
        Empresa empresa = empresaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com o ID: " + id));
        mapToEmpresa(dto, empresa);
        Empresa atualizada = empresaRepository.save(empresa);
        return new ContaResponseDTO(atualizada);
    }

    //utilizado em vagas
    @Transactional(readOnly = true)
    public Empresa buscarEmpresaPorId(UUID id) {
        Empresa empresa = empresaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com o ID: " + id));
        return empresa;
    }

    private void mapToEmpresa(EmpresaRequestDTO dto, Empresa empresa) {
        empresa.setNome(dto.nome());
        empresa.setEmail(dto.email());
        empresa.setTelefone(dto.telefone());
        empresa.setEndereco(dto.endereco());
        empresa.setSenha(dto.senha());
        empresa.setCnpj(dto.cnpj());
        empresa.setBiografia(dto.biografia());
        empresa.setLinks(dto.links());
    }

}
