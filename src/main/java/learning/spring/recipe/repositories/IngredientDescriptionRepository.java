package learning.spring.recipe.repositories;

import learning.spring.recipe.model.IngredientDescription;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IngredientDescriptionRepository extends CrudRepository<IngredientDescription, Long> {
    Optional<IngredientDescription> findByIdAndRecipeId(Long ingredientId, Long recipeId);
}
