package sara.projeto.saraEmprega.ports;

import sara.projeto.saraEmprega.model.Curriculum;
import sara.projeto.saraEmprega.util.jwt.ContaAutenticada;

public interface CurriculumServerPort {
    public Curriculum getCurriculum(ContaAutenticada auth);
    public Curriculum setCurriculum(
        Curriculum curriculum,
        ContaAutenticada Auth
    );
}
