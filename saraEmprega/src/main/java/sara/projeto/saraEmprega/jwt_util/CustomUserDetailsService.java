package sara.projeto.saraEmprega.jwt_util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.UserServicePort;

import java.util.Optional;

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
        return new UserAuthenticaded(toReturn);
    }
}
