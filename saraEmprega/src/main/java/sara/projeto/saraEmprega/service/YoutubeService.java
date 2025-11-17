package sara.emprega.msusers.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sara.emprega.msusers.util.youtube.YoutubeApiResponse;
import sara.emprega.msusers.util.youtube.YoutubeFeignClient;
import sara.emprega.msusers.util.youtube.YoutubeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class YoutubeService {

    private final YoutubeFeignClient youtubeFeignClient;

    @Value("${youtube.api.key}")
    private String apiKey;

    public int getVideoDuration(String videoUrl) {
        String videoId = YoutubeUtils.extractVideoId(videoUrl);
        YoutubeApiResponse response = youtubeFeignClient.getVideoDetails( "contentDetails", videoId, apiKey );

        if (response != null && response.items != null && !response.items.isEmpty()) {
            String isoDuration = response.items.get(0).contentDetails.duration;
            return convertISO8601ToReadable(isoDuration);
        }
            return 0;
    }

    private int convertISO8601ToReadable(String isoDuration) {
        if (isoDuration == null || isoDuration.isEmpty()) {
            return 0;
        }

        Pattern pattern = Pattern.compile("^PT(?:(\\d+)H)?(?:(\\d+)M)?$");
        Matcher matcher = pattern.matcher(isoDuration);

        if (matcher.matches()) {
            int hours = matcher.group(1) != null ? Integer.parseInt(matcher.group(1)) : 0;
            int minutes = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : 0;

            return hours * 60 + minutes;
        }
        throw new IllegalArgumentException("Formato de duração inválido: ");
    }

}

