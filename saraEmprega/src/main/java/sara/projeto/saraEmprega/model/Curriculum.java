package sara.projeto.saraEmprega.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

    @Entity
    @Table(name = "curriculums")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public class Curriculum {

        @Id
        //@GeneratedValue //a gente ta gerando com @GeneratedValue, mas acho que da no mesmo
        private UUID id = UUID.randomUUID();

        @Lob
        @Column(columnDefinition = "LONGBLOB")
        private byte[] data;

        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;

    }


