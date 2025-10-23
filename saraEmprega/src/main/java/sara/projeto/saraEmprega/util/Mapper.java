package sara.projeto.saraEmprega.util;

import org.springframework.web.multipart.MultipartFile;
import sara.projeto.saraEmprega.model.Curriculum;

import java.io.IOException;

public class Mapper {

    public static Curriculum mapToCurriculum(MultipartFile file) throws IOException {
        //relacionar com user posteriormente
        return Curriculum.builder()
                .data(file.getBytes())
                .build();
    }
}
