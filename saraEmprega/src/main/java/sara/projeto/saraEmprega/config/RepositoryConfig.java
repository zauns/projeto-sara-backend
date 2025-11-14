package sara.projeto.saraEmprega.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sara.projeto.saraEmprega.adapter.ContaRepositoryAdapter;
import sara.projeto.saraEmprega.model.Empresa;
import sara.projeto.saraEmprega.model.Secretaria;
import sara.projeto.saraEmprega.model.Administrador;
import sara.projeto.saraEmprega.ports.ContaRepositoryPort;
import sara.projeto.saraEmprega.repository.EmpresaRepository;
import sara.projeto.saraEmprega.repository.SecretariaRepository;
import sara.projeto.saraEmprega.repository.AdministradorRepository;

@Configuration
public class RepositoryConfig {

    @Bean
    public ContaRepositoryPort<Empresa> empresaRepositoryPort(EmpresaRepository repository) {
        return new ContaRepositoryAdapter<Empresa>() {
            @Override
            protected org.springframework.data.jpa.repository.JpaRepository<Empresa, java.util.UUID> getRepositorio() {
                return repository;
            }
            @Override
            public Optional<Empresa> encontrarPorEmail(String email) {
                return repository.findByEmail(email);
            }
        };
    }

    @Bean
    public ContaRepositoryPort<Secretaria> secretariaRepositoryPort(SecretariaRepository repository) {
        return new ContaRepositoryAdapter<Secretaria>() {
            @Override
            protected org.springframework.data.jpa.repository.JpaRepository<Secretaria, java.util.UUID> getRepositorio() {
                return repository;
            }
            @Override
            public Optional<Secretaria> encontrarPorEmail(String email) {
                return repository.findByEmail(email);
            }
        };
    }

    @Bean
    public ContaRepositoryPort<Administrador> administradorRepositoryPort(AdministradorRepository repository) {
        return new ContaRepositoryAdapter<Administrador>() {
            @Override
            protected org.springframework.data.jpa.repository.JpaRepository<Administrador, java.util.UUID> getRepositorio() {
                return repository;
            }
            @Override
            public Optional<Administrador> encontrarPorEmail(String email) {
                return repository.findByEmail(email);
            }
        };
    }
}
