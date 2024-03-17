package learning.spring.recipe.mappers;

import learning.spring.recipe.model.Recipe;
import learning.spring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeToIdMapperTest {

    @InjectMocks
    private RecipeToIdMapper mapper;
    @Mock
    private RecipeRepository recipeRepository;

    private final Long id = 1L;

    @Test
    void toRecipe() {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(id);

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        var mappedRecipe = mapper.toRecipe(id);

        // then
        assertNotNull(mappedRecipe);
        assertEquals(id, mappedRecipe.getId());
    }

    @Test
    void toRecipeNullId() {
        assertNull(mapper.toRecipe(null));
    }

    @Test
    void toId() {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(id);

        //when
        var mappedId = mapper.toId(recipe);

        // then
        assertNotNull(mappedId);
        assertEquals(id, mappedId);
    }

    @Test
    void toIdNullRecipe() {
        assertNull(mapper.toId(null));
    }
}