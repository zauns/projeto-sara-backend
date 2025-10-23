package sara.projeto.saraEmprega.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenBlacklistService {

    private final StringRedisTemplate redisTemplate;
    private static final String PREFIX = "blacklisted_token:";

    public void revokeToken(String token, long timeToLiveSeconds) {
        redisTemplate.opsForValue().set(PREFIX + token, "revoked", Duration.ofSeconds(timeToLiveSeconds));
    }

    // Verifica se o token foi revogado
    public boolean isRevoked(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + token));
    }
}
