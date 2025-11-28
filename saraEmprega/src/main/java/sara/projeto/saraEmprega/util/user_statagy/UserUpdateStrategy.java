package sara.projeto.saraEmprega.util.user_statagy;


import sara.projeto.saraEmprega.dto.UserRequestDTO;
import sara.projeto.saraEmprega.model.User;

public interface UserUpdateStrategy {
    boolean update(User user, UserRequestDTO dto);
}
