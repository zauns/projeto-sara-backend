package sara.emprega.msusers.util.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sara.emprega.msusers.model.Account;
import sara.emprega.msusers.util.AuthorityUtils;

import java.util.Collection;
@Getter
@RequiredArgsConstructor
public class UserAuthenticated implements UserDetails {

    private final Account user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.convertRolesToAuthorities(user.getRoles());
    }

    public String getTypeOf(){
        return user.getClass().getSimpleName();
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
