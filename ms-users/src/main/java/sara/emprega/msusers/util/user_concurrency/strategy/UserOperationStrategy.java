package sara.emprega.msusers.util.user_concurrency.strategy;

import sara.emprega.msusers.enums.UserAction;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.util.user_concurrency.abstractions.UserOperation;

public interface UserOperationStrategy <T extends UserOperation>{
    User execute(T op);
    UserAction getUserAction();
    @SuppressWarnings("unchecked")
    default User executeUnchecked(UserOperation op) {
        return execute((T) op);
    }
}
