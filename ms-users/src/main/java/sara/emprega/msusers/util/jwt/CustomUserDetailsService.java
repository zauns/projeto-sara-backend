package sara.emprega.msusers.util.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.ports.UserServicePort;

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
