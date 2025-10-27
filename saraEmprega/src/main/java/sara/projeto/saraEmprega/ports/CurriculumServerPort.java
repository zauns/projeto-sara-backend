package sara.projeto.saraEmprega.ports;

import sara.emprega.msusers.dto.CurriculumDTO;
import sara.emprega.msusers.model.Curriculum;
import sara.emprega.msusers.util.jwt.UserAuthenticated;

public interface CurriculumServerPort {
    public Curriculum getCurriculum();
    public Curriculum setCurriculum(
        Curriculum curriculum,
        UserAuthenticated Auth
    );
}
