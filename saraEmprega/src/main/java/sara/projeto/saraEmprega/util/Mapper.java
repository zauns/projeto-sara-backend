package sara.projeto.saraEmprega.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sara.projeto.saraEmprega.dto.UserDTO;
import sara.projeto.saraEmprega.model.Document;
import sara.projeto.saraEmprega.model.User;

import java.io.IOException;
import java.util.UUID;

public class Mapper {

    @Value("${spring.util.encoderStrength}")
    private int encoderStrong;

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

    public static User MapToUser(UserDTO userDTO) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
        return User.builder()
                .email(userDTO.email())
                .nome(userDTO.name())
                .senhaHash(passwordEncoder.encode(userDTO.password()))
                .build();
    }

    public static UserDTO mapToUserRequestDTO(User user){
        return new UserDTO(user.getNome(), user.getEmail(),  user.getSenhaHash());
    }
}
