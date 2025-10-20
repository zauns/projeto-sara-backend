package sara.emprega.msusers.util;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {

    SecretKey getSigningKey() {
        return Jwts.SIG.HS256.key().build();
    }

    

}
