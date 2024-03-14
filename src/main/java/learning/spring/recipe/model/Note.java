package learning.spring.recipe.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"id","recipe"})
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @NonNull private String notes;
    @OneToOne(mappedBy = "note")
    @ToString.Exclude
    private Recipe recipe;
}
