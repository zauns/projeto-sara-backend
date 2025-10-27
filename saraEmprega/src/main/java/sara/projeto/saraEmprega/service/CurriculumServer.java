package sara.projeto.saraEmprega.service;

import sara.projeto.saraEmprega.model.Curriculum;
import sara.projeto.saraEmprega.ports.CurriculumServerPort;
import sara.projeto.saraEmprega.util.jwt.UserAuthenticated;

public class CurriculumServer implements CurriculumServerPort {
    @Override
    public Curriculum getCurriculum() {
        return null;
    }

    @Override
    public Curriculum setCurriculum(Curriculum curriculum, UserAuthenticated Auth) {
        return null;
    }
}
