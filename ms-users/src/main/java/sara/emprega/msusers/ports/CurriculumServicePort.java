package sara.emprega.msusers.ports;

import sara.emprega.msusers.model.Curriculum;
import sara.emprega.msusers.util.jwt.UserAuthenticated;

public interface CurriculumServicePort {

    public Curriculum getCurriculum(String mail);
    public void setCurriculum(Curriculum curriculum, String mail);
}
