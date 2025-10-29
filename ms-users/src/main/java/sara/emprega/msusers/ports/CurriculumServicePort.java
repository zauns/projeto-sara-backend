package sara.emprega.msusers.ports;

import sara.emprega.msusers.model.Curriculum;
import sara.emprega.msusers.util.jwt.UserAuthenticated;

public interface CurriculumServicePort {

    public Curriculum getCurriculum(UserAuthenticated Auth);
    public void setCurriculum(Curriculum curriculum, UserAuthenticated Auth);
}
