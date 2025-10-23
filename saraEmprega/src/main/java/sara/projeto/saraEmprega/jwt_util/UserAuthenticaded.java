package sara.projeto.saraEmprega.jwt_util;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.UserServicePort;
import sara.projeto.saraEmprega.util.AuthorityUtils;

import java.util.Collection;
@RequiredArgsConstructor
public class UserAuthenticaded implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.convertRolesToAuthorities(user.getRoles());
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    @Override
    public String getPassword() {
        return user.getHashedPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
