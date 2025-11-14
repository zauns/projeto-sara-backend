package sara.projeto.saraEmprega.util.jwt;

import java.util.Collection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sara.projeto.saraEmprega.model.Conta;
import sara.projeto.saraEmprega.util.AuthorityUtils;

@Getter
@RequiredArgsConstructor
public class UserAuthenticated implements UserDetails {

    private final Conta user;

    public String getTypeOf() {
        return user.getClass().getSimpleName();
    }

    @Override
    public String getPassword() {
        return user.getSenhaHash();
    }

    @Override
    public String getUsername() {
        return user.getNome();
    }

    public String getEmail(){
        return user.getEmail();
    }
}
