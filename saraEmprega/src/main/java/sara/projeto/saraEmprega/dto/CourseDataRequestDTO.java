package sara.projeto.saraEmprega.dto;

import java.util.List;

public record CourseDataRequestDTO(String name,List<String> tags, String description, int length,List<String> Creators) {
}
