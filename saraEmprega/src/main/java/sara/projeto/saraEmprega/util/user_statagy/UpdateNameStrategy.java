package sara.projeto.saraEmprega.util.user_statagy;

import sara.projeto.saraEmprega.dto.UserDTO;
import sara.projeto.saraEmprega.model.User;

public class UpdateNameStrategy implements UserUpdateStrategy {

    @Override
    public boolean update(User user, UserDTO dto) {
        if (dto.name() != null && !dto.name().equals(user.getNome())) {
            user.setNome(dto.name());
            return true;
        }
        return false;
    }
}
