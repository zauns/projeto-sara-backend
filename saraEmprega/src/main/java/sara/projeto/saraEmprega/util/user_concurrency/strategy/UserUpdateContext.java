package sara.projeto.saraEmprega.util.user_concurrency.strategy;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.util.user_concurrency.abstractions.UserOperation;

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
