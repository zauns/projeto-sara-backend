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
import sara.projeto.saraEmprega.util.Mapper;

@Service
@RequiredArgsConstructor
public class AdministradorService extends ContaService<Administrador> implements AdministradorServicePort {

    private final ContaRepositoryPort<Administrador> repositorio;
    private final Mapper mapper;

    @Override
    protected ContaRepositoryPort<Administrador> repositorio() {
        return this.repositorio;
    }

    @Override
    @Transactional
    public ContaResponseDTO criar(AdministradorRequestDTO dto) {
        Administrador administrador = new Administrador();
        administrador = mapper.administradorParaEntidade(dto);
        repositorio.salvar(administrador);
        return new ContaResponseDTO(administrador);
    }

    @Override
    @Transactional
    public ContaResponseDTO atualizar(UUID id, AdministradorRequestDTO dto){
        Administrador administrador = repositorio.encontrarPorId(id).orElseThrow(() -> new EntityNotFoundException("Administrador não encontrado"));
        mapper.atualizaAdministradorDeDTO(dto, administrador);
        repositorio.salvar(administrador);
        return new ContaResponseDTO(administrador);
    }

    @Transactional
    public AdministradorRequestDTO getDados(UUID id){
        Administrador administrador = repositorio.encontrarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Administrador não encontrado"));
                    return AdministradorRequestDTO.converter(administrador);
    }


}
