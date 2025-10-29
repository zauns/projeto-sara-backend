/*
package sara.emprega.msusers.repository;
>>>>>>>> 06f735752fbcc028c83f7c3fb527063abf02ce34:ms-users/src/main/java/sara/emprega/msusers/unused/TokenRepository.java

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor

//TODO

    /*
*
*
*
* essa classe vai servir para logout da conta por meio do block do tokenJWT (OPCIONAL)
*

public class TokenRepository {

    private final RedisTemplate redisTemplate;

    private final long expirationTime = 3600L;

    private static final String PREFIX = "users:access:";
    private static final String REFRESH_PREFIX = "users:access:";

    //key prefixes token blacklist
    private static final String ACESS_BLACKLIST_PREFIX = "blacklist:access:";
    private static final String REFREX_PREFIX = "blacklist:access:";

    public void storeToken(String username, String accessToken, String refreshToken) {

        //store
        String acessKey = PREFIX + username;
        storeToken(acessKey, accessToken, refreshToken);
        //refresh
        String refreshKey = REFRESH_PREFIX + username;
        storeToken(refreshKey, refreshToken, expirationTime);
    }

    public String getAccessToken(String username) {
        String acessKey = PREFIX + username;
        return getToken(acessKey);
    }

    public void removeAllTokens(String username) {
        String acessToken = getAccessToken(username);
        String refreshToken = getRefreshToken(username);
    }

    public String getRefreshToken(String username) {
        String acessKey = REFRESH_PREFIX + username;
        return getToken(acessKey);
    }

    private String getToken(String acessKey) {
        Object token = redisTemplate.opsForValue().get(acessKey);
        return token != null ? token.toString() : null;
    }

    private void storeToken(String key, String token, long expiration) {
        redisTemplate.opsForValue().set(key, token);
        redisTemplate.expire(key, expiration , TimeUnit.MILLISECONDS);

    }

}

 */
