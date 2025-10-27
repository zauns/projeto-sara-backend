package sara.projeto.saraEmprega.ports;

import sara.projeto.saraEmprega.model.Curriculum;
import sara.projeto.saraEmprega.util.jwt.UserAuthenticated;

public interface CurriculumServerPort {
    public Curriculum getCurriculum();
    public Curriculum setCurriculum(
        Curriculum curriculum,
        UserAuthenticated Auth
    );
}
