package sara.projeto.saraEmprega.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sara.projeto.saraEmprega.model.Curriculum;
import sara.projeto.saraEmprega.ports.CurriculumServerPort;
import sara.projeto.saraEmprega.util.jwt.ContaAutenticada;

@Service
@RequiredArgsConstructor
public class CurriculumServer implements CurriculumServerPort {

    private final CurriculumService curriculumService;

    @Override
    public Curriculum getCurriculum(ContaAutenticada auth) {
        //return curriculumService.getCurriculum(auth.getEmail());
        return null;
    }

    @Override
    public Curriculum setCurriculum(
        Curriculum curriculum,
        ContaAutenticada auth
    ) {
        curriculumService.setCurriculum(curriculum, auth.getEmail());
        return curriculum;
    }

}
