package sara.projeto.saraEmprega.util;

import sara.projeto.saraEmprega.model.User;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import sara.projeto.saraEmprega.dto.UserRequestDTO;

@ExtendWith(MockitoExtension.class)
class MapperTest {
    @Mock
    PasswordEncoder encoder;
    @InjectMocks
    Mapper mapper;

    @Test
    void deveEncodarSenhaAoMapearUsuario() {
        UserRequestDTO dto = new UserRequestDTO("Nome", "email", "123", "tel", "end");

        when(encoder.encode("123")).thenReturn("hash123");

        User user = mapper.userParaEntidade(dto);

        Assertions.assertEquals("hash123", user.getSenhaHash());
    }
}
