package sara.projeto.saraEmprega.service;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.transaction.Transactional;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.EmpresaRequestDTO;
import sara.projeto.saraEmprega.model.Empresa;
import sara.projeto.saraEmprega.repository.EmpresaRepository;

public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Transactional
    public ContaResponseDTO criar(EmpresaRequestDTO dto){
        Empresa empresa = new Empresa();
        mapToEmpresa(dto, empresa);
        Empresa novaEmpresa = empresaRepository.save(empresa);                                                                                                                                    
        return new ContaResponseDTO(novaEmpresa);
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
