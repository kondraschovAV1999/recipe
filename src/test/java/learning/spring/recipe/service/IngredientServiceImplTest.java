package learning.spring.recipe.service;

import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.mappers.*;
import learning.spring.recipe.model.Ingredient;
import learning.spring.recipe.model.IngredientDescription;
import learning.spring.recipe.model.Recipe;
import learning.spring.recipe.model.UnitOfMeasure;
import learning.spring.recipe.repositories.IngredientDescriptionRepository;
import learning.spring.recipe.repositories.IngredientRepository;
import learning.spring.recipe.repositories.RecipeRepository;
import learning.spring.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class IngredientServiceImplTest {
    private IngredientDescriptionMapper mapper;

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private IngredientDescriptionRepository ingredientDescriptionRepository;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {

        mapper = new IngredientDescriptionMapperImpl(
                new IngredientMapperImpl(),
                new UnitOfMeasureMapperImpl(),
                new RecipeToIdMapper(recipeRepository)
        );

        ingredientService = new IngredientServiceImpl(
                mapper,
                ingredientDescriptionRepository,
                unitOfMeasureRepository,
                ingredientRepository,
                recipeRepository
        );
    }

    @Test
    public void findByRecipeIdAndRecipeIdHappyPath() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        IngredientDescription ingredientDesc1 = new IngredientDescription();
        ingredientDesc1.setId(1L);
        ingredientDesc1.setAmount(new BigDecimal(1));

        IngredientDescription ingredientDesc2 = new IngredientDescription();
        ingredientDesc2.setId(2L);
        ingredientDesc2.setAmount(new BigDecimal(2));

        IngredientDescription ingredientDesc3 = new IngredientDescription();
        ingredientDesc3.setId(3L);
        ingredientDesc3.setAmount(new BigDecimal(3));


        recipe.addIngredientDescription(ingredientDesc1);
        recipe.addIngredientDescription(ingredientDesc2);
        recipe.addIngredientDescription(ingredientDesc3);
        Optional<IngredientDescription> ingredientDescriptionOptional = Optional.of(ingredientDesc3);

        when(ingredientDescriptionRepository.findByIdAndRecipeId(
                anyLong(), anyLong())).thenReturn(ingredientDescriptionOptional);

        //then
        IngredientDescriptionDTO dto = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //when
        assertEquals(3L, dto.getId());
        assertEquals(1L, dto.getRecipeId());
        verify(ingredientDescriptionRepository).findByIdAndRecipeId(
                anyLong(), anyLong());
    }
    @Test
    void testSaveIngredientDto() {
        //given
        Long ingredientDescId = 3L;
        Long recipeId = 2L;
        Long ingredientId = 1L;
        Long uomId = 2L;

        IngredientDescription ingredientDescription = new IngredientDescription();
        ingredientDescription.setId(ingredientDescId);

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.addIngredientDescription(ingredientDescription);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        ingredient.setDescription("test description");
        ingredientDescription.setIngredient(ingredient);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(uomId);
        ingredientDescription.setUnitOfMeasure(uom);


        when(ingredientDescriptionRepository.findById(anyLong())).thenReturn(
                Optional.of(ingredientDescription));
        when(ingredientRepository.findByDescription(anyString())).thenReturn(
                Optional.of(ingredient));
        when(unitOfMeasureRepository.findById(anyLong())).thenReturn(
                Optional.of(uom));
        when(ingredientDescriptionRepository.save(any())).thenReturn(
                ingredientDescription);
        when(recipeRepository.findById(anyLong())).thenReturn(
                Optional.of(recipe));

        //when
        IngredientDescriptionDTO savedDto = ingredientService.saveIngredientDto(
                mapper.toDto(ingredientDescription));

        //then
        assertEquals(ingredientDescId, savedDto.getId());
        assertEquals(recipeId, savedDto.getRecipeId());
        assertEquals(ingredientId, savedDto.getIngredient().getId());
        assertEquals(uomId, savedDto.getUom().getId());
        verify(ingredientDescriptionRepository).findById(anyLong());
        verify(ingredientRepository).findByDescription(anyString());
        verify(unitOfMeasureRepository).findById(anyLong());
        verify(ingredientDescriptionRepository).save(any(IngredientDescription.class));
        verify(recipeRepository).findById(anyLong());
    }

    @Test
    void testDeleteByIdAndRecipeId() {
        //given
        Long id = 1L;
        IngredientDescription ingredientDescription = new IngredientDescription();
        ingredientDescription.setId(id);

        Long recipeId = 2L;
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.addIngredientDescription(ingredientDescription);


        when(recipeRepository.findById(anyLong())).thenReturn(
                Optional.of(recipe));
        when(ingredientDescriptionRepository.findById(anyLong())).thenReturn(
                Optional.of(ingredientDescription));

        //when
        ingredientService.deleteByIdAndRecipeId(id, recipeId);

        //then
        assertEquals(0, recipe.getIngredients().size());
        assertNull(ingredientDescription.getRecipe());

        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository).save(any(Recipe.class));
        verify(ingredientDescriptionRepository).deleteById(anyLong());
    }
}