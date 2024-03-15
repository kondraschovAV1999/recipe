package learning.spring.recipe.service;

import jakarta.transaction.Transactional;
import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.mappers.RecipeMapper;
import learning.spring.recipe.model.Recipe;
import learning.spring.recipe.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
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

        var recipeOptional = recipeRepository.findById(id);

        if (recipeOptional.isEmpty()) {
            log.warn("Recipe Not Found");
            throw new RuntimeException("Recipe Not Found");
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeDTO saveRecipeDto(RecipeDTO dto) {
        Recipe detachedRecipe = mapper.fromDto(dto);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId: " + savedRecipe.getId());
        return mapper.toDto(savedRecipe);
    }

}
