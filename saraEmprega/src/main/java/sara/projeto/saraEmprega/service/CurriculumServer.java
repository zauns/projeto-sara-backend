package sara.emprega.msusers.service;

import sara.emprega.msusers.model.Curriculum;
import sara.emprega.msusers.ports.CurriculumServerPort;
import sara.emprega.msusers.util.jwt.UserAuthenticated;

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
