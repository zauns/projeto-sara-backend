package sara.projeto.saraEmprega.model;


import jakarta.persistence.*;
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
        @Builder.Default
        private UUID id = UUID.randomUUID();

        @Lob
        @Column(columnDefinition = "LONGBLOB")
        private byte[] data;

        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;
    }


