package sara.projeto.saraEmprega.ports;

import sara.projeto.saraEmprega.model.Curriculum;
import sara.projeto.saraEmprega.util.jwt.UserAuthenticated;

public interface CurriculumServicePort {

    public Curriculum getCurriculum(UserAuthenticated Auth);
    public void setCurriculum(Curriculum curriculum, UserAuthenticated Auth);
}
