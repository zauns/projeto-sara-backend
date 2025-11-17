package sara.emprega.msusers.util.user_concurrency.abstractions;

import lombok.Getter;
import sara.emprega.msusers.dto.UserDTO;
import sara.emprega.msusers.enums.UserAction;

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
