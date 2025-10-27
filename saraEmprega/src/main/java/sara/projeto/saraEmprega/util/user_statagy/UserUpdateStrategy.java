package sara.projeto.saraEmprega.util.user_statagy;

import sara.emprega.msusers.dto.UserDTO;
import sara.emprega.msusers.model.User;

public interface UserUpdateStrategy {
    boolean update(User user, UserDTO dto);
}
