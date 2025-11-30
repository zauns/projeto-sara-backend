package sara.projeto.saraEmprega.util.user_statagy;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sara.projeto.saraEmprega.dto.UserRequestDTO;
import sara.projeto.saraEmprega.model.User;

@Component
@RequiredArgsConstructor
public class UpdateContext {

    private final List<UserUpdateStrategy> strategies;

    public void execute(User user, UserRequestDTO dto) {
        long updatedCount = strategies
            .stream()
            .mapToInt(strategy -> strategy.update(user, dto) ? 1 : 0)
            .sum();

        if (updatedCount == 0) {
            throw new IllegalArgumentException(
                "campos inseridos incorretamente"
            );
        }
    }
}
