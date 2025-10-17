package sara.emprega.msusers.util;

import org.springframework.web.multipart.MultipartFile;
import sara.emprega.msusers.model.Curriculum;

import java.io.IOException;

public class Mapper {

    public static Curriculum mapToCurriculum(MultipartFile file) throws IOException {
        //relacionar com buider posteriormente
        return Curriculum.builder()
                .fileName("curriculo.pdf")
                .fileType("application/pdf")
                .data(file.getBytes())
                .build();
    }
}
