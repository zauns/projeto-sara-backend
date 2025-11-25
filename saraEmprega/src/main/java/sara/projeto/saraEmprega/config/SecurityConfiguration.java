package sara.projeto.saraEmprega.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration {

    //@Value("${spring.util.encoderStrength}")
    //private int encoderStrong;


    @Value("${jwt.public-key}")
    RSAPublicKey key;

    @Value("${jwt.private-key}")
    RSAPrivateKey priv;

    /**
     * Configura a cadeia de filtros de segurança para a aplicação SaraEmprega.
     *
     *   Autorização de endpoints públicos:
     *   Permite acesso não autenticado a:
     *
     *   Criação de empresas e secretarias (POST /empresa, POST /secretaria)
     *   Endpoint de autenticação (/token)
     *   Endpoints públicos da API (/api/public/**)
     *   Endpoints de health check e informações (/health, /actuator/health, /actuator/info)
     *
     *   Proteção de recursos: Todos os demais endpoints exigem autenticação
     *   Configuração JWT: Configura o servidor de recursos OAuth2 para usar JWT
     *   com um conversor personalizado de autenticação
     *   Sessões stateless: Define a aplicação como sem estado (stateless),
     *       não mantendo sessões no servidor
     *   Proteção CSRF: Desabilita CSRF para APIs REST stateless
     *   Tratamento de exceções: Configura handlers específicos para tokens bearer
     *       inválidos e acesso negado
     *
     * Fluxo de segurança:
     * 1. Cliente envia credenciais para /token → recebe JWT<br>
     * 2. Cliente envia JWT no header Authorization: Bearer {token}<br>
     * 3. Servidor valida JWT e converte para Authentication object<br>
     * 4. Authorization checks baseados nas roles do usuário autenticado</p>
     *
     * Esta configuração é automaticamente aplicada pelo Spring Security na inicialização
     * da aplicação, definindo o comportamento de segurança para todos os endpoints.
     *
     * @param http o objeto HttpSecurity para configurar a segurança web
     * @return SecurityFilterChain configurado para a aplicação
     * @throws Exception se ocorrer erro durante a configuração
     *
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {
        http
                .cors(Customizer.withDefaults()) //libera o acesso do nevegador
                .authorizeHttpRequests((authorize) -> authorize
                    .requestMatchers(HttpMethod.POST, "/empresa").permitAll()
                    .requestMatchers(HttpMethod.POST, "/secretaria").permitAll()
                    .requestMatchers("/token",

                        "/api/public/**",
                        "/health",
                        "/actuator/health",
                        "/actuator/info"

                    ).permitAll()
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .anyRequest().authenticated()
                )
                .csrf((csrf) -> csrf.disable())
                .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                );
        // @formatter:on
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource (){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(
            new JWKSet(jwk)
        );
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("scope");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
}
