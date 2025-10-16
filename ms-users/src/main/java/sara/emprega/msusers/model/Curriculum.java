package sara.emprega.msusers.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

    @Entity
    @Table(name = "curriculums")
    @AllArgsConstructor
    @NoArgsConstructor
    public class Curriculum {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private UUID id;

        private String fileName;

        private String fileType;

        @Lob
        @Column(columnDefinition = "LONGBLOB")
        private byte[] data;

        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;

    }


