package sara.projeto.saraEmprega.util.jwt;

import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import sara.projeto.saraEmprega.model.Administrador;
import sara.projeto.saraEmprega.model.Conta;

/**
 * Implementação de UserDetails para autenticação com contas do sistema
 *
 * Esta classe adapta qualquer entidade que estenda Conta para o sistema
 * de autenticação do Spring Security.
 *
 * Funcionamento:
 * - Encapsula uma Conta (User, Empresa, Secretaria, Administrador)
 * - Determina as roles/autoridades baseadas no tipo específico da conta
 * - Fornece os dados necessários para o processo de autenticação
 *
 * Mapeamento de roles:
 * - Administrador (isSuperAdmin = true) → ROLE_SUPER_ADMIN
 * - Administrador (isSuperAdmin = false) → ROLE_ADMIN
 * - Empresa → ROLE_EMPRESA
 * - Secretaria → ROLE_SECRETARIA
 * - User → ROLE_USER
 *
 * Todas as contas são consideradas ativas por padrão (isEnabled = true)
 * Contas de Empresa/Secretaria podem ser bloqueadas via isValidada no serviço
 */
@Getter
@RequiredArgsConstructor
public class ContaAutenticada implements UserDetails { // transferi pro tipo conta para abrangir todos os usuários

    private final Conta conta;

    public String getTypeOf() {
        return conta.getClass().getSimpleName();
    }

    @Override
    public String getPassword() {
        String hash = conta.getSenhaHash();
        return (hash != null) ? hash.trim() : null;
    }

    @Override
    public String getUsername() {
        return conta.getNome();
    }

    public String getEmail() {
        return conta.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = getContaAuthority();
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    private String getContaAuthority() {
        switch (conta.getClass().getSimpleName()) {
            case "Administrador":
                Administrador admin = (Administrador) conta;
                return admin.isSuperAdmin() ? "ROLE_SUPER_ADMIN" : "ROLE_ADMIN";
            case "Empresa":
                return "ROLE_EMPRESA";
            case "Secretaria":
                return "ROLE_SECRETARIA";
            case "User":
                return "ROLE_USER";
            default:
                return "ROLE_USER";
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
