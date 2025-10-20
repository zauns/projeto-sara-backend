package sara.emprega.msusers.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private UUID id = UUID.randomUUID();
    private String firstName;
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Curriculum curriculum;
}
