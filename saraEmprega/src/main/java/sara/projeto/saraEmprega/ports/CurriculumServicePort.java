package sara.projeto.saraEmprega.ports;

import sara.projeto.saraEmprega.model.Curriculum;

public interface CurriculumServicePort {

    public Curriculum getCurriculum(String mail);
    public void setCurriculum(Curriculum curriculum, String mail);
}
