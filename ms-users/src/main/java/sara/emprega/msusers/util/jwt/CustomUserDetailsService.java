<<<<<<<< HEAD:saraEmprega/src/main/java/sara/projeto/saraEmprega/jwt_util/CustomUserDetailsService.java
package sara.projeto.saraEmprega.jwt_util;
========
package sara.emprega.msusers.util.jwt;
>>>>>>>> 06f735752fbcc028c83f7c3fb527063abf02ce34:ms-users/src/main/java/sara/emprega/msusers/util/jwt/CustomUserDetailsService.java

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.UserServicePort;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServicePort  userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User toReturn = userService.getUserByMail(username);
        if(toReturn == null){
            throw new UsernameNotFoundException(username);
        }
        return new UserAuthenticated(toReturn);
    }
}
