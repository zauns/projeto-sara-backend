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

    private CurriculumRepositoryPort curriculumRepository;
    private UserServicePort userService;

    @Override
    public Curriculum getCurriculum(UserAuthenticated userAuth) {
        User user = (User) userAuth.getUser();
        if (user.getCurriculum() == null){
            throw new UserNotFoundException("curriculo nao encontrado");
        }
        return user.getCurriculum();
    }

    @Override
    public void setCurriculum(Curriculum curriculum, UserAuthenticated auth) {
        User user = (User) auth.getUser();
        user.setCurriculum(curriculum);
        curriculum.setUser(user);
        userService.curriculumUpdate(user);
    }

}
