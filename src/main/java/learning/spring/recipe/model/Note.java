package learning.spring.recipe.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Lob
    @NonNull private String notes;
    @OneToOne(mappedBy = "note")
    private Recipe recipe;

}
