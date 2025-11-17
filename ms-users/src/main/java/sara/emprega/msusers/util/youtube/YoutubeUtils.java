package sara.emprega.msusers.util.youtube;

import lombok.AllArgsConstructor;
import sara.emprega.msusers.exception.VideoNotFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeUtils {

    private static final Pattern VIDEO_ID_PATTERN = Pattern.compile(
            "(?<=v=)[^&]+|" +     // formato padrão: watch?v=VIDEO_ID
                    "(?<=be/)[^?&]+|" +   // formato curto: youtu.be/VIDEO_ID
                    "(?<=embed/)[^?&]+|" +// formato embed: /embed/VIDEO_ID
                    "(?<=shorts/)[^?&]+"  // formato shorts: /shorts/VIDEO_ID
    );

    public static String extractVideoId(String youtubeUrl) {
        if (youtubeUrl == null || youtubeUrl.isBlank()) {
            throw new VideoNotFoundException("A URL do YouTube não pode ser nula ou vazia.");
        }

        Matcher matcher = VIDEO_ID_PATTERN.matcher(youtubeUrl.trim());
        if (matcher.find()) {
            return matcher.group();
        }

        throw new VideoNotFoundException("Não foi possível extrair o ID do vídeo da URL: " + youtubeUrl);
    }
}