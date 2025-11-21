package sara.projeto.saraEmprega.util.user_concurrency.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sara.projeto.saraEmprega.enums.UserAction;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.service.UserService;
import sara.projeto.saraEmprega.util.user_concurrency.abstractions.UserOperationCreate;

@Component
@RequiredArgsConstructor
public class CreateUserStrategy implements UserOperationStrategy<UserOperationCreate> {

    private final UserService service;

    @Override
    public User execute(UserOperationCreate op) {
        return service.createUser(op.getClaims(), op.getUser());
    }

    @Override
    public UserAction getUserAction() {
        return UserAction.CREATE_USER;
    }
}
