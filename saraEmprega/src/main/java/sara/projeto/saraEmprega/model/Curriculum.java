package sara.projeto.saraEmprega.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "curriculums")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Curriculum {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
