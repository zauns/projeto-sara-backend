package sara.projeto.saraEmprega.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Account {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String email;
    private String hashedPassword;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}