package learning.spring.recipe.service;

import jakarta.transaction.Transactional;
import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.mappers.RecipeMapper;
import learning.spring.recipe.model.Recipe;
import learning.spring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RecipeServiceIT {

    private static final String NEW_DESCRIPTION = "New Description";
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeMapper recipeMapper;

    @Test
    @Transactional
    void testSaveOfDescription() {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeDTO testRecipeDto = recipeMapper.toDto(testRecipe);

        //when
        testRecipeDto.setDescription(NEW_DESCRIPTION);
        RecipeDTO savedRecipeDto = recipeService.saveRecipeDto(testRecipeDto);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeDto.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeDto.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeDto.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeDto.getIngredients().size());
    }
}
