package learning.spring.recipe.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id","recipe"})
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String notes;
    @OneToOne(mappedBy = "note")
    @ToString.Exclude
    private Recipe recipe;

    public Note(String notes) {
        this.notes = notes;
    }

    public Note(Long id, String notes) {
        this.id = id;
        this.notes = notes;
    }
}
