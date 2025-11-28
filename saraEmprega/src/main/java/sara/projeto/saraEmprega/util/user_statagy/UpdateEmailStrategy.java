package sara.projeto.saraEmprega.util.user_statagy;

import org.springframework.stereotype.Component;
import sara.projeto.saraEmprega.dto.UserRequestDTO;
import sara.projeto.saraEmprega.model.User;

@Component
public class UpdateEmailStrategy implements UserUpdateStrategy {

    @Override
    public boolean update(User user, UserRequestDTO dto) {
        if (dto.email() != null && !dto.email().equals(user.getEmail())) {
            user.setEmail(dto.email());
            return true;
        }
        return false;
    }
}
