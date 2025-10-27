package sara.projeto.saraEmprega.util.user_statagy;

import org.springframework.stereotype.Component;
import sara.emprega.msusers.dto.UserDTO;
import sara.emprega.msusers.model.User;

@Component
public class UpdateEmailStrategy implements UserUpdateStrategy {

    @Override
    public boolean update(User user, UserDTO dto) {
        if (dto.email() != null && !dto.email().equals(user.getEmail())) {
            user.setEmail(dto.email());
            return true;
        }
        return false;
    }
}
