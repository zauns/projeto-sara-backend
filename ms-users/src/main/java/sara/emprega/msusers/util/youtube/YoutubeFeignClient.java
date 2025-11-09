package sara.emprega.msusers.util.youtube;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "youtubeClient", url = "${youtube.api.url}")
public interface YoutubeFeignClient {

    @GetMapping("/videos")
    YoutubeApiResponse getVideoDetails(
            @RequestParam("part") String part,
            @RequestParam("id") String videoId,
            @RequestParam("key") String apiKey
    );
}
