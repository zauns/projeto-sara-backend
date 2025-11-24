package sara.projeto.saraEmprega.util.jwt;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sara.projeto.saraEmprega.model.Conta;
import sara.projeto.saraEmprega.model.Empresa;
import sara.projeto.saraEmprega.model.Secretaria;
import sara.projeto.saraEmprega.repository.ContaRepository;

/**
 * Serviço customizado para carregar detalhes do usuário durante a autenticação
 *
 * Este serviço é chamado automaticamente pelo Spring Security durante o login
 * para buscar os dados do usuário baseado no username (email) fornecido.
 *
 * Fluxo de autenticação:
 * Usuário envia email e senha no login
 * Spring Security chama este serviço passando o email como username
 * Busca a conta no repositório pelo email
 * Verifica se contas de Empresa/Secretaria estão validadas
 * Retorna ContaAutenticada se tudo estiver OK
 * Spring Security compara a senha e cria a autenticação
 *
 * Validações específicas:
 * Empresas e Secretarias precisam estar validadas (isValidada = true)
 * Usuários e Administradores não precisam de validação adicional
 * Contas não validadas lançam LockedException (conta bloqueada)
 *
 * O username usado é o email da conta, não o nome
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ContaRepository repositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Conta toReturn = repositorio.findByEmail(username).orElseThrow(
            () -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        if (toReturn instanceof Empresa empresa) {
            if (!empresa.isValidada()) {
                throw new LockedException("Conta da empresa '" + username + "' aguardando aprovação.");
            }
        } else if (toReturn instanceof Secretaria secretaria) {
            if (!secretaria.isValidada()) {
                throw new LockedException("Conta da secretaria '" + username + "' aguardando aprovação.");
            }
        }
        return new ContaAutenticada(toReturn);
    }
}
