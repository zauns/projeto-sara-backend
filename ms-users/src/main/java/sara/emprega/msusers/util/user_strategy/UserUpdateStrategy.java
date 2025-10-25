package sara.emprega.msusers.util.user_strategy;


import sara.emprega.msusers.dto.UserDTO;
import sara.emprega.msusers.model.User;

public interface UserUpdateStrategy {
    boolean update(User user, UserDTO dto);
}
