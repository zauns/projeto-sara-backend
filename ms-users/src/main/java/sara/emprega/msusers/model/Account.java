package sara.emprega.msusers.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Account {
    @Id
    private UUID id = UUID.randomUUID();
    private String email;
    private String hashedPassword;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

}