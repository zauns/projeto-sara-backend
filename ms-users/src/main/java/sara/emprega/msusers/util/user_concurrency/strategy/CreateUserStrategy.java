package sara.emprega.msusers.util.user_concurrency.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sara.emprega.msusers.enums.UserAction;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.service.UserService;
import sara.emprega.msusers.util.user_concurrency.abstractions.UserOperationCreate;

@Component
@RequiredArgsConstructor
public class CreateUserStrategy implements UserOperationStrategy<UserOperationCreate> {

    private final UserService service;

    @Override
    public User execute(UserOperationCreate op) {
        return service.CreateUser(op.getClaims(), op.getUser());
    }

    @Override
    public UserAction getUserAction() {
        return UserAction.CREATE_USER;
    }
}
