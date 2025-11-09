package sara.projeto.saraEmprega.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import sara.projeto.saraEmprega.dto.AdministradorRequestDTO;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.model.Administrador;
import sara.projeto.saraEmprega.repository.AdministradorRepository;

@Service
public class AdministradorService extends ContaService<Administrador> {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    protected JpaRepository<Administrador, UUID> repositorio() {
        return administradorRepository;
    }

    @Transactional
    public ContaResponseDTO criar(AdministradorRequestDTO dto) {
        Administrador administrador = new Administrador();
        mapToAdministrador(dto, administrador);
        administradorRepository.save(administrador);
        return new ContaResponseDTO(administrador);
    }

    @Transactional
    public ContaResponseDTO atualizar(UUID id, AdministradorRequestDTO dto){
        Administrador administrador = administradorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Administrador não encontrado"));
        mapToAdministrador(dto, administrador);
        administradorRepository.save(administrador);
        return new ContaResponseDTO(administrador);
    }


    private void mapToAdministrador(AdministradorRequestDTO dto, Administrador administrador) {
        administrador.setNome(dto.nome());
        administrador.setEmail(dto.email());
        administrador.setTelefone(dto.telefone());
        administrador.setEndereco(dto.endereco());
        administrador.setSenha(dto.senha());
        administrador.setSuperAdmin(dto.isSuperAdmin());
    }

}
