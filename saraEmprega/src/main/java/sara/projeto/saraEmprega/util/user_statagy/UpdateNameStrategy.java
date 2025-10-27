package sara.projeto.saraEmprega.util.user_statagy;

import sara.emprega.msusers.dto.UserDTO;
import sara.emprega.msusers.model.User;

public class UpdateNameStrategy implements UserUpdateStrategy {

    @Override
    public boolean update(User user, UserDTO dto) {
        if (dto.name() != null && !dto.name().equals(user.getFirstName())) {
            user.setEmail(dto.name());
            return true;
        }
        return false;
    }
}
