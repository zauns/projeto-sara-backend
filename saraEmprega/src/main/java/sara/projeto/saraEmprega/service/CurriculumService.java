package sara.projeto.saraEmprega.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sara.projeto.saraEmprega.exception.UserNotFoundException;
import sara.projeto.saraEmprega.model.Curriculum;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.CurriculumRepositoryPort;
import sara.projeto.saraEmprega.ports.CurriculumServicePort;
import sara.projeto.saraEmprega.ports.UserServicePort;
import sara.projeto.saraEmprega.util.jwt.UserAuthenticated;

@Transactional
@Service
@AllArgsConstructor
public class CurriculumService implements CurriculumServicePort {

    private UserServicePort userService;

    @Override
    public Curriculum getCurriculum(String userMail) {
        User user = userService.findByEmail(userMail);
        if (user.getCurriculum() == null){
            throw new UserNotFoundException("curriculo nao encontrado");
        }
        return user.getCurriculum();
    }

    @Override
    public void setCurriculum(Curriculum curriculum, String mail) {
        User user = userService.findByEmail(mail);
        user.setCurriculum(curriculum);
        curriculum.setUser(user);
        userService.updateCurriculum(user);
    }

}
