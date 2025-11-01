package sara.emprega.msusers.util.user_concurrency.strategy;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.util.user_concurrency.abstractions.UserOperation;

import java.util.List;

@AllArgsConstructor
@Component
public class UserUpdateContext {

    private final List<UserOperationStrategy<?>> strategies;

    public User execute(UserOperation userOperation) {
        return strategies.stream()
                .filter(strategy -> strategy.getUserAction() == userOperation.getAction())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Operação não suportada: " + userOperation.getAction()))
                .executeUnchecked(userOperation);
    }
}
