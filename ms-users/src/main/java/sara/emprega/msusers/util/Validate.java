package sara.emprega.msusers.util;

import org.springframework.web.multipart.MultipartFile;

public class Validate {

    public static void validatePDF(MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo está vazio");
        }

        if (!"application/pdf".equals(file.getContentType())) {
            throw new IllegalArgumentException("Apenas arquivos PDF são permitidos");
        }

        if (file.getSize() > 5_000_000) { // máximo 5MB
            throw new IllegalArgumentException("O arquivo é muito grande. Máx: 5MB");
        }
    }
}
