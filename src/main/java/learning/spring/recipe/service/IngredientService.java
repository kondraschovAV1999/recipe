package learning.spring.recipe.service;

import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.model.IngredientDescription;

public interface IngredientService {
    IngredientDescriptionDTO findByRecipeIdAndIngredientId(long recipeId, long id);
    IngredientDescriptionDTO saveIngredientDto(IngredientDescriptionDTO dto);
    IngredientDescription createIngredientDescFromForm(IngredientDescriptionDTO dto);
}
