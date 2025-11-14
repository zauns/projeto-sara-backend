package sara.projeto.saraEmprega.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import sara.projeto.saraEmprega.dto.AdministradorRequestDTO;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.model.Administrador;
import sara.projeto.saraEmprega.ports.AdministradorServicePort;
import sara.projeto.saraEmprega.ports.ContaRepositoryPort;

@Service
@RequiredArgsConstructor
public class AdministradorService extends ContaService<Administrador> implements AdministradorServicePort {

    private final ContaRepositoryPort<Administrador> repositorio;

    @Override
    protected ContaRepositoryPort<Administrador> repositorio() {
        return this.repositorio;
    }

    @Override
    @Transactional
    public ContaResponseDTO criar(AdministradorRequestDTO dto) {
        Administrador administrador = new Administrador();
        mapToAdministrador(dto, administrador);
        repositorio.salvar(administrador);
        return new ContaResponseDTO(administrador);
    }

    @Override
    @Transactional
    public ContaResponseDTO atualizar(UUID id, AdministradorRequestDTO dto){
        Administrador administrador = repositorio.encontrarPorId(id).orElseThrow(() -> new EntityNotFoundException("Administrador não encontrado"));
        mapToAdministrador(dto, administrador);
        repositorio.salvar(administrador);
        return new ContaResponseDTO(administrador);
    }


    private void mapToAdministrador(AdministradorRequestDTO dto, Administrador administrador) {
        administrador.setNome(dto.nome());
        administrador.setEmail(dto.email());
        administrador.setTelefone(dto.telefone());
        administrador.setEndereco(dto.endereco());
        administrador.setSenhaHash(dto.senha());
        administrador.setSuperAdmin(dto.isSuperAdmin());
    }


}
