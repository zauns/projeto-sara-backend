package sara.emprega.msusers.util.user_strategy;

import sara.emprega.msusers.dto.UserDTO;
import sara.emprega.msusers.model.User;

public class UpdateNameStrategy  implements UserUpdateStrategy {

    @Override
    public boolean update(User user, UserDTO dto) {
        if (dto.name() != null && !dto.name().equals(user.getFirstName())) {
            user.setEmail(dto.name());
            return true;
        }
        return false;
    }
}
