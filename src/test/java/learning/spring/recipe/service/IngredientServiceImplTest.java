package learning.spring.recipe.service;

import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.mappers.*;
import learning.spring.recipe.model.IngredientDescription;
import learning.spring.recipe.model.Recipe;
import learning.spring.recipe.repositories.IngredientDescriptionRepository;
import learning.spring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class IngredientServiceImplTest {
    private IngredientDescriptionMapper mapper;

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private IngredientDescriptionRepository ingredientDescriptionRepository;

    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        mapper = new IngredientDescriptionMapperImpl(
                new IngredientMapperImpl(),
                new UnitOfMeasureMapperImpl(),
                new RecipeToIdMapper(recipeRepository)
        );

        ingredientService = new IngredientServiceImpl(mapper, ingredientDescriptionRepository);
    }

    @Test
    public void findByRecipeIdAndRecipeIdHappyPath() throws Exception {
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
}