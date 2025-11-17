package sara.emprega.msusers.util.user_concurrency.strategy;

import lombok.RequiredArgsConstructor;
import sara.emprega.msusers.enums.UserAction;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.ports.UserServicePort;
import org.springframework.stereotype.Component;
import sara.emprega.msusers.util.user_concurrency.abstractions.UserOperation;

@Component
@RequiredArgsConstructor
public class GetUserByMailStrategy implements UserOperationStrategy<UserOperation> {
    private final UserServicePort userServicePort;

    @Override
    public User execute(UserOperation operation) {
        String mail = operation.getMail();
        return userServicePort.getUserByMail(mail);
    }

    @Override
    public UserAction getUserAction() {
        return UserAction.GET_USER_BY_MAIL;
    }
}
