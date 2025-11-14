package sara.projeto.saraEmprega.util.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sara.projeto.saraEmprega.model.Conta;
import sara.projeto.saraEmprega.ports.ContaServicePort;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ContaServicePort contaService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Conta toReturn = contaService.buscarPorEmail(username).orElseThrow(
            () -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        return new ContaAutenticada(toReturn);
    }
}
