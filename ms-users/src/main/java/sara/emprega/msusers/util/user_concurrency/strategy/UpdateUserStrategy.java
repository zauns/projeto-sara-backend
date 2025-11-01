package sara.emprega.msusers.util.user_concurrency.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sara.emprega.msusers.dto.UserDTO;
import sara.emprega.msusers.enums.UserAction;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.service.UserService;
import sara.emprega.msusers.util.user_concurrency.abstractions.UserOperationUpdate;

@Component
@RequiredArgsConstructor
public class UpdateUserStrategy implements UserOperationStrategy<UserOperationUpdate> {

    private final UserService userService;

    @Override
    public User execute(UserOperationUpdate op) {
        UserDTO userDTO = op.getDto();
        String mail = op.getMail();
        return userService.updateUser(userDTO, mail);
    }

    @Override
    public UserAction getUserAction() {
        return UserAction.UPDATE_USER;
    }
}

