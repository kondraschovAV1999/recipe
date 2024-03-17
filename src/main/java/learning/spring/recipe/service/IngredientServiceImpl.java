package learning.spring.recipe.service;

import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.mappers.IngredientDescriptionMapper;
import learning.spring.recipe.repositories.IngredientDescriptionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientDescriptionMapper mapper;
    private final IngredientDescriptionRepository repository;

    @Override
    public IngredientDescriptionDTO findByRecipeIdAndIngredientId(long recipeId, long ingredientId) {

        return mapper.toDto(
                repository.findByIdAndRecipeId(ingredientId, recipeId)
                        .orElseThrow(() -> {
                            log.error("Ingredient Not Found by ingredientId:%d and recipeId:%d".formatted(ingredientId, recipeId));
                            return new RuntimeException("Ingredient Not Found by ingredient and recipe ids");
                        }));
    }
}
