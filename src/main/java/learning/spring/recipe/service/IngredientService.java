package learning.spring.recipe.service;

import learning.spring.recipe.dto.IngredientDescriptionDTO;

public interface IngredientService {
    IngredientDescriptionDTO findByRecipeIdAndIngredientId(long recipeId, long id);
    IngredientDescriptionDTO saveIngredientDto(IngredientDescriptionDTO dto);
}
