package learning.spring.recipe.mappers;

import learning.spring.recipe.model.Recipe;
import learning.spring.recipe.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RecipeToIdMapper {
    private final RecipeRepository recipeRepository;

    public Recipe toRecipe(Long recipeId) {
        if (recipeId == null) return null;

        var recipeOptional = recipeRepository.findById(recipeId);

        //todo implement error handling
        return recipeOptional.orElse(null);
    }

    public Long toId(Recipe recipe) {
        return recipe != null ? recipe.getId() : null;
    }
}
