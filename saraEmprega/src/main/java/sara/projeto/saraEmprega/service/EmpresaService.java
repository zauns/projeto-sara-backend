package sara.projeto.saraEmprega.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import sara.projeto.saraEmprega.dto.EmpresaRequestDTO;
import sara.projeto.saraEmprega.dto.EmpresaResponseDTO;
import sara.projeto.saraEmprega.repository.ContaRepository;

public class EmpresaService {
    @Autowired
    private ContaRepository contaRepository;

    @Transactional
    public ContaResponseDTO criarEmpresa (EmpresaRequestDTO dto) {
        Empresa empresa = new Empresa();
        mapToEmpresa(dto, empresa);

        Empresa empresaSalva = contaRepository.save(empresa);
        return new ContaResponseDTO(empresaSalva);
    }
}
