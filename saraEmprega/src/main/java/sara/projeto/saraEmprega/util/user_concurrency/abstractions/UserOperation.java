package sara.projeto.saraEmprega.util.user_concurrency.abstractions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sara.projeto.saraEmprega.enums.UserAction;

@AllArgsConstructor
@Getter
public class UserOperation {
    private UserAction action;
    private String mail;
}
