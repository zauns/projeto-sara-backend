package sara.emprega.msusers.util.user_concurrency.abstractions;

import lombok.Getter;
import sara.emprega.msusers.enums.UserAction;
import sara.emprega.msusers.model.User;
@Getter
public class UserOperationCreate extends UserOperation{
    User user;
    String claims;

    public UserOperationCreate(UserAction action, String mail) {
        super(action, mail);
    }

    public UserOperationCreate(UserAction action, String mail, User user, String claims) {
        super(action, mail);
        this.user = user;
        this.claims = claims;
    }

}
