package sara.projeto.saraEmprega.util.user_concurrency.strategy;

import sara.projeto.saraEmprega.enums.UserAction;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.util.user_concurrency.abstractions.UserOperation;

public interface UserOperationStrategy <T extends UserOperation>{
    User execute(T op);
    UserAction getUserAction();
    @SuppressWarnings("unchecked")
    default User executeUnchecked(UserOperation op) {
        return execute((T) op);
    }
}
