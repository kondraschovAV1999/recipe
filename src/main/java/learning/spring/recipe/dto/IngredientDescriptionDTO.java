package learning.spring.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDescriptionDTO {
    private Long id;
    private IngredientDTO ingredient;
    private String amount;
    private UnitOfMeasureDTO uom;
}
