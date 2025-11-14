package sara.projeto.saraEmprega.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sara.projeto.saraEmprega.model.Curriculum;
import sara.projeto.saraEmprega.ports.CurriculumServerPort;
import sara.projeto.saraEmprega.util.jwt.UserAuthenticated;

@Service
@RequiredArgsConstructor
public class CurriculumServer implements CurriculumServerPort {

    private final CurriculumService curriculumService;

    @Override
    public Curriculum getCurriculum(UserAuthenticated auth) {
        return curriculumService.getCurriculum(auth.getEmail());
    }

    @Override
    public Curriculum setCurriculum(
        Curriculum curriculum,
        UserAuthenticated auth
    ) {
        curriculumService.setCurriculum(curriculum, auth.getEmail());
        return curriculum;
    }
}
