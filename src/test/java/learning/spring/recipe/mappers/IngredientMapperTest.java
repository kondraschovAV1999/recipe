package learning.spring.recipe.mappers;

import learning.spring.recipe.dto.IngredientDTO;
import learning.spring.recipe.model.Ingredient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IngredientMapperTest {
    private final IngredientMapper mapper = IngredientMapper.INSTANCE;

    @Test
    public void toDto() {
        // Given
        Ingredient ingredient = new Ingredient(1L, "Test Description");

        // When
        IngredientDTO dto = mapper.toDto(ingredient);

        // Then
        assertEquals(ingredient.getId(), dto.getId());
        assertEquals(ingredient.getDescription(), dto.getDescription());
    }

    @Test
    public void toDtoEmptyObject() {
        // Given
        Ingredient ingredient = new Ingredient();

        // When
        IngredientDTO dto = mapper.toDto(ingredient);

        // Then
        assertNull(dto.getId());
        assertNull(dto.getDescription());
    }

    @Test
    public void toDtoNullInput() {
        // When
        IngredientDTO dto = mapper.toDto(null);

        // Then
        assertNull(dto);
    }

    @Test
    public void fromDto() {
        // Given
        IngredientDTO dto = new IngredientDTO(1L, "Test Description");

        // When
        Ingredient ingredient = mapper.fromDto(dto);

        // Then
        assertEquals(dto.getId(), ingredient.getId());
        assertEquals(dto.getDescription(), ingredient.getDescription());
    }

    @Test
    public void fromDtoEmptyObject() {
        // Given
        IngredientDTO dto = new IngredientDTO();

        // When
        Ingredient ingredient = mapper.fromDto(dto);

        // Then
        assertNull(ingredient.getId());
        assertNull(ingredient.getDescription());
    }

    @Test
    public void fromDtoNullInput() {
        // When
        Ingredient ingredient = mapper.fromDto(null);

        // Then
        assertNull(ingredient);
    }


}