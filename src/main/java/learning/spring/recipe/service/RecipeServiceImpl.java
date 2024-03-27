package learning.spring.recipe.service;

import jakarta.transaction.Transactional;
import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.exceptions.NotFoundException;
import learning.spring.recipe.mappers.RecipeMapper;
import learning.spring.recipe.model.IngredientDescription;
import learning.spring.recipe.model.Recipe;
import learning.spring.recipe.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final RecipeMapper mapper;

    @Override
    public List<Recipe> getRecipes() {
        log.debug("I'm in the service");
        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> {
            String message = "Recipe with id=%d Not Found".formatted(id);
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    @Transactional
    public RecipeDTO saveRecipeDto(RecipeDTO dto) {
        Recipe detachedRecipe = mapper.fromDto(dto);

        detachedRecipe.setIngredients(
                dto.getIngredients().stream()
                        .map(i -> {
                            IngredientDescription newIngredientDesc = ingredientService.createIngredientDescFromForm(i);
                            newIngredientDesc.setRecipe(detachedRecipe);
                            return newIngredientDesc;
                        })
                        .collect(Collectors.toSet()));

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId: " + savedRecipe.getId());
        return mapper.toDto(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeDTO findDtoById(Long id) {
        return mapper.toDto(findById(id));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public void deleteIngredient(int index, RecipeDTO recipe) {
        log.debug("Deleting ingredient from recipe");

        if (recipe != null) recipe.removeIngredientByIndex(index);
        else {
            String message = "Recipe for ingredientDesc with id=%d Not Found".formatted(index);
            log.error(message);
            throw new NotFoundException(message);
        }

    }
}
