package sara.emprega.msusers.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sara.emprega.msusers.exception.UserNotFoundException;
import sara.emprega.msusers.model.Curriculum;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.ports.CurriculumRepositoryPort;
import sara.emprega.msusers.ports.CurriculumServicePort;
import sara.emprega.msusers.ports.UserServicePort;
import sara.emprega.msusers.util.jwt.UserAuthenticated;

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
