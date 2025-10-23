package sara.emprega.msusers.ports;

import org.springframework.security.core.Authentication;

public interface TokenServicesPort {
    public String token(Authentication authentication);
}
