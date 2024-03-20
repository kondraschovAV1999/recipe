package learning.spring.recipe.service;

import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.model.Recipe;
import learning.spring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    @InjectMocks
    RecipeServiceImpl recipeService;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    IngredientService ingredientService;

    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRecipes() {

        Recipe recipe = new Recipe();
        List<Recipe> recipesData = new ArrayList<>();
        recipesData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipesData);

        List<Recipe> recipes = recipeService.getRecipes();
        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(ID);
        var recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(ID);

        assertNotNull(recipeReturned, "Null recipe returned");
        verify(recipeRepository).findById(anyLong());
    }

    @Test
    void testDeleteById() {
        //given
        Long id = 2L;

        //when
        recipeService.deleteById(id);

        //then
        verify(recipeRepository).deleteById(anyLong());
    }

    @Test
    void testDeleteIngredient() {
        //given
        int index = 0;
        Long recipeId = 1L;
        Long ingredientId = 1L;
        RecipeDTO recipeDto = new RecipeDTO();
        recipeDto.setId(recipeId);

        IngredientDescriptionDTO ingredientDescriptionDto = new IngredientDescriptionDTO();
        ingredientDescriptionDto.setId(ingredientId);
        recipeDto.getIngredients().add(ingredientDescriptionDto);
        ingredientDescriptionDto.setRecipeId(recipeId);

        //when
        recipeService.deleteIngredient(index, recipeDto);

        //then
        assertEquals(0, recipeDto.getIngredients().size());
        assertNull(ingredientDescriptionDto.getRecipeId());
        verify(ingredientService).deleteById(anyLong());
    }

}