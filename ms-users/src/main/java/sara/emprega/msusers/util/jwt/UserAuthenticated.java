<<<<<<<< HEAD:saraEmprega/src/main/java/sara/projeto/saraEmprega/jwt_util/UserAuthenticaded.java
package sara.projeto.saraEmprega.jwt_util;
========
package sara.emprega.msusers.util.jwt;
>>>>>>>> 06f735752fbcc028c83f7c3fb527063abf02ce34:ms-users/src/main/java/sara/emprega/msusers/util/jwt/UserAuthenticated.java

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
<<<<<<<< HEAD:saraEmprega/src/main/java/sara/projeto/saraEmprega/jwt_util/UserAuthenticaded.java
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.UserServicePort;
import sara.projeto.saraEmprega.util.AuthorityUtils;
========
import sara.emprega.msusers.model.Account;
import sara.emprega.msusers.util.AuthorityUtils;
>>>>>>>> 06f735752fbcc028c83f7c3fb527063abf02ce34:ms-users/src/main/java/sara/emprega/msusers/util/jwt/UserAuthenticated.java

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
