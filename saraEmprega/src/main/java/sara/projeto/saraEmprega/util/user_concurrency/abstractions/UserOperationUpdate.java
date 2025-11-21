package sara.projeto.saraEmprega.util.user_concurrency.abstractions;

import lombok.Getter;
import sara.projeto.saraEmprega.dto.UserDTO;
import sara.projeto.saraEmprega.enums.UserAction;

@Getter
public class UserOperationUpdate extends UserOperation {
    UserDTO dto;

    public UserOperationUpdate(UserAction action, String mail, UserDTO dto) {
        super(action, mail);
        this.dto = dto;
    }

    public UserOperationUpdate(UserAction action, String mail) {
        super(action, mail);
    }

}
