package learning.spring.recipe.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(exclude = {"id", "recipe"})
@Entity
public class IngredientDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Ingredient ingredient; // unidirectional mapping
    @ManyToOne
    private UnitOfMeasure unitOfMeasure; // unidirectional mapping
    @ManyToOne
    private Recipe recipe;
    private BigDecimal amount;
}
