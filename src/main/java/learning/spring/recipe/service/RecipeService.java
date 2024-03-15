package learning.spring.recipe.service;

import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.model.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeDTO saveRecipeDto(RecipeDTO dto);
}
