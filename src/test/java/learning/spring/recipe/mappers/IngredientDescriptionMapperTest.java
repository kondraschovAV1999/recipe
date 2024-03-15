package learning.spring.recipe.mappers;

import learning.spring.recipe.dto.IngredientDTO;
import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.dto.UnitOfMeasureDTO;
import learning.spring.recipe.model.Ingredient;
import learning.spring.recipe.model.IngredientDescription;
import learning.spring.recipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IngredientDescriptionMapperTest {

    private IngredientDescriptionMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new IngredientDescriptionMapperImpl(
                new IngredientMapperImpl(),
                new UnitOfMeasureMapperImpl()
        );
    }

    @Test
    public void toDto() {
        // Given
        IngredientDescription ingredientDescription = new IngredientDescription();
        ingredientDescription.setId(1L);
        ingredientDescription.setAmount(BigDecimal.TEN);

        Ingredient ingredient = new Ingredient(1L, "Test Ingredient");
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure(1L, "Test UOM");
        ingredientDescription.setIngredient(ingredient);
        ingredientDescription.setUnitOfMeasure(unitOfMeasure);

        // When
        IngredientDescriptionDTO dto = mapper.toDto(ingredientDescription);

        // Then
        assertEquals(ingredientDescription.getId(), dto.getId());
        assertEquals(ingredient.getId(), dto.getIngredient().getId());
        assertEquals(unitOfMeasure.getId(), dto.getUmo().getId());

        assertEquals(ingredientDescription.getAmount(), new BigDecimal(dto.getAmount()));
    }

    @Test
    public void toDtoNullInput() {
        // When
        IngredientDescriptionDTO dto = mapper.toDto(null);

        // Then
        assertNull(dto);
    }

    @Test
    public void fromDto() {
        // Given
        IngredientDTO ingredientDTO = new IngredientDTO(1L, "Test Ingredient");
        UnitOfMeasureDTO unitOfMeasureDTO = new UnitOfMeasureDTO(1L, "Test UOM");

        IngredientDescriptionDTO dto = new IngredientDescriptionDTO();
        dto.setId(1L);
        dto.setAmount("10");
        dto.setIngredient(ingredientDTO);
        dto.setUmo(unitOfMeasureDTO);

        // When
        IngredientDescription ingredientDescription = mapper.fromDto(dto);

        // Then
        assertEquals(dto.getId(), ingredientDescription.getId());
        assertEquals(new BigDecimal(dto.getAmount()), ingredientDescription.getAmount());
        assertEquals(dto.getIngredient().getId(), ingredientDescription.getIngredient().getId());
        assertEquals(dto.getUmo().getId(), ingredientDescription.getUnitOfMeasure().getId());
    }

    @Test
    public void fromDtoNullInput() {
        // When
        IngredientDescription ingredientDescription = mapper.fromDto(null);

        // Then
        assertNull(ingredientDescription);
    }


}