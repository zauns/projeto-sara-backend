package sara.projeto.saraEmprega.ports;


import sara.projeto.saraEmprega.model.Curriculum;

public interface CurriculumServicePort {

    public byte[] getCurriculum(String mail);
    public void setCurriculum(Curriculum curriculum, String mail);
}
