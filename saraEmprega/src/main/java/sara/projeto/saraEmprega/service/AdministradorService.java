package sara.projeto.saraEmprega.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import sara.projeto.saraEmprega.dto.AdministradorRequestDTO;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.model.Administrador;
import sara.projeto.saraEmprega.ports.AdministradorServicePort;
import sara.projeto.saraEmprega.ports.ContaRepositoryPort;

@Service
public class AdministradorService extends ContaService<Administrador> implements AdministradorServicePort {

    private final ContaRepositoryPort<Administrador> repositorio;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected ContaRepositoryPort<Administrador> repositorio() {
        return this.repositorio;
    }

    public AdministradorService(ContaRepositoryPort<Administrador> repositorio, PasswordEncoder passwordEncoder) {
        this.repositorio = repositorio;
        this.passwordEncoder = passwordEncoder;
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
        administrador.setSenhaHash(passwordEncoder.encode(dto.senha()));
        administrador.setSuperAdmin(dto.isSuperAdmin());
    }

}
