package sara.projeto.saraEmprega.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import sara.projeto.saraEmprega.dto.AdministradorRequestDTO;
import sara.projeto.saraEmprega.dto.EmpresaRequestDTO;
import sara.projeto.saraEmprega.dto.SecretariaRequestDTO;
import sara.projeto.saraEmprega.dto.UserRequestDTO;
import sara.projeto.saraEmprega.model.Administrador;
import sara.projeto.saraEmprega.model.Document;
import sara.projeto.saraEmprega.model.Empresa;
import sara.projeto.saraEmprega.model.Secretaria;
import sara.projeto.saraEmprega.model.User;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Mapper {

    @Value("${spring.util.encoderStrength:4}")
    private static int encoderStrength;


    private final PasswordEncoder passwordEncoder;


    public void setEncoderStrength(int strength) {
        encoderStrength = strength;
    }

    public static Document mapToCurriculum(String fileName) throws IOException {

        return Document.builder()
                .documentType("curriculum")
                .documentName(fileName)
                .id(UUID.randomUUID())
                .build();
    }

    public static Document mapToContentPDF(String fileName) throws IOException {
        return Document.builder()
                .documentType("content")
                .documentName(fileName)
                .id(UUID.randomUUID())
                .build();
    }

    public void atualizaUserDeDTO(UserRequestDTO dto, User user) {
            user.setNome(dto.name());
            user.setEmail(dto.email());
            user.setTelefone(dto.telefone());
            user.setEndereco(dto.endereco());

            // Lógica centralizada de senha
            if (dto.password() != null && !dto.password().isBlank()) {
                user.setSenhaHash(passwordEncoder.encode(dto.password()));
            }
        }

    public User userParaEntidade(UserRequestDTO dto) {
        User user = new User();
        atualizaUserDeDTO(dto, user);
        return user;
    }

    public void atualizaEmpresaDeDTO(EmpresaRequestDTO dto, Empresa empresa) {
            empresa.setNome(dto.nome());
            empresa.setEmail(dto.email());
            empresa.setTelefone(dto.telefone());
            empresa.setEndereco(dto.endereco());
            empresa.setCnpj(dto.cnpj());
            empresa.setBiografia(dto.biografia());
            empresa.setLinks(dto.links());
            if (dto.senha() != null && !dto.senha().isBlank()) {
                empresa.setSenhaHash(passwordEncoder.encode(dto.senha()));
            }
        }

        public Empresa empresaParaEntidade(EmpresaRequestDTO dto) {
            Empresa empresa = new Empresa();
            atualizaEmpresaDeDTO(dto, empresa);
            empresa.setValidada(false);
            return empresa;
        }

        public void atualizaSecretariaDeDTO(SecretariaRequestDTO dto, Secretaria secretaria) {
            secretaria.setNome(dto.nome());
            secretaria.setEmail(dto.email());
            secretaria.setTelefone(dto.telefone());
            secretaria.setEndereco(dto.endereco());
            secretaria.setMunicipio(dto.municipio());

            if (dto.senha() != null && !dto.senha().isBlank()) {
                secretaria.setSenhaHash(passwordEncoder.encode(dto.senha()));
            }
        }

        public Secretaria secretariaParaEntidade(SecretariaRequestDTO dto) {
            Secretaria secretaria = new Secretaria();
            atualizaSecretariaDeDTO(dto, secretaria);
            secretaria.setValidada(false);
            return secretaria;
        }

        public void atualizaAdministradorDeDTO(AdministradorRequestDTO dto, Administrador admin) {
            admin.setNome(dto.nome());
            admin.setEmail(dto.email());
            admin.setTelefone(dto.telefone());
            admin.setEndereco(dto.endereco());
            admin.setSuperAdmin(dto.isSuperAdmin());

            if (dto.senha() != null && !dto.senha().isBlank()) {
                admin.setSenhaHash(passwordEncoder.encode(dto.senha()));
            }
        }

        public Administrador administradorParaEntidade(AdministradorRequestDTO dto) {
            Administrador admin = new Administrador();
            atualizaAdministradorDeDTO(dto, admin);
            return admin;
        }
}
