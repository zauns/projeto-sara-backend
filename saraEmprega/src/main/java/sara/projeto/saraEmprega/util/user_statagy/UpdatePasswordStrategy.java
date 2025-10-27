package sara.projeto.saraEmprega.util.user_statagy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sara.projeto.saraEmprega.dto.UserDTO;
import sara.projeto.saraEmprega.model.User;

@Component
public class UpdatePasswordStrategy implements UserUpdateStrategy {

    @Value("${spring.util.encoderStrength}")
    private int encoderStrength;

    @Override
    public boolean update(User user, UserDTO dto) {
        if (
            dto.password() != null &&
            !user.getHashedPassword().equals(dto.password())
        ) {
            PasswordEncoder encoder = new BCryptPasswordEncoder(
                encoderStrength
            );
            user.setEmail(encoder.encode(dto.password()));
            return true;
        }
        return false;
    }
}
