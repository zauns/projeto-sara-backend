package sara.projeto.saraEmprega.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.UserRequestDTO;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.ContaRepositoryPort;
import sara.projeto.saraEmprega.ports.UserServicePort;
import sara.projeto.saraEmprega.util.user_statagy.UpdateContext;

//TODO
@Service
@Transactional
@AllArgsConstructor
public class UserService extends ContaService<User> implements UserServicePort {

    private final ContaRepositoryPort<User> repositorio;
    private final UpdateContext updateContext;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected ContaRepositoryPort<User> repositorio() {
        return this.repositorio;
    }

    @Transactional
    public User curriculumUpdate(User user) {
        return repositorio.salvar(user);
    }

    @Override
    @Transactional
    public ContaResponseDTO create(UserRequestDTO dto) {
        User user = new User();
        mapToUser(dto, user);
        repositorio.salvar(user);
        return new ContaResponseDTO(user);
    }

    @Override
    @Transactional
    public ContaResponseDTO update(UUID id, UserRequestDTO dto) {
        User user = repositorio.encontrarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        mapToUser(dto, user);
        repositorio.salvar(user);
        return new ContaResponseDTO(user);
    }

    @Transactional
    public User getUserByMail(String email) {
        Optional<User> userOptional = repositorio.encontrarPorEmail(email);
        User user = userOptional.get();
        return user;
    }

    private void mapToUser(UserRequestDTO dto, User user) {
            user.setNome(dto.name());
            user.setEmail(dto.email());
            user.setSenhaHash(passwordEncoder.encode(dto.password()));
            // Adicione outros campos se necessário
    }
}

/*
 * @Override
 * public void updateUserRoles(UUID id, List<String> roles) {
 *
 * }
 *
 * @Override
 * public void deleteUserById(UUID id) {
 *
 * }
 *
 * @Override
 * public void deleteUserByMail(String mail) {
 *
 * }
 *
 * @Override
 * public boolean existsByMail(String mail) {
 * return false;
 * }
 */
