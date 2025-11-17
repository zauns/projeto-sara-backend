package sara.emprega.msusers.util.user_concurrency.abstractions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import sara.emprega.msusers.enums.UserAction;

@AllArgsConstructor
@Getter
public class UserOperation {
    private UserAction action;
    private String mail;
}
