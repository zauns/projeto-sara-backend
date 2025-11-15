package sara.emprega.msusers.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

    @Entity
    @Table(name = "documents")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public class Document {

        @Id
        @Builder.Default
        private UUID id = UUID.randomUUID();

        private String pathR2;
        private String documentName;
        private String documentType;

        @OneToOne
        @JoinColumn
        private User user;

        @ManyToOne
        @JoinColumn
        private Module module;
    }