package sara.projeto.saraEmprega.util.user_concurrency.abstractions;

import lombok.Getter;
import sara.projeto.saraEmprega.enums.UserAction;
import sara.projeto.saraEmprega.model.User;
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
