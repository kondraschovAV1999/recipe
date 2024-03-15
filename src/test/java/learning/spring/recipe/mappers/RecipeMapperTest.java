package learning.spring.recipe.mappers;

import learning.spring.recipe.dto.CategoryDTO;
import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.dto.NoteDTO;
import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RecipeMapperTest {
    public static final String DESCRIPTION = "Test Recipe";
    public static final LocalTime PREP_TIME = LocalTime.of(1, 0);
    public static final LocalTime COOK_TIME = LocalTime.of(0, 30);
    public static final int SERVINGS = 4;
    public static final String SOURCE = "Test Source";
    public static final String URL = "Test URL";
    public static final String DIRECTIONS = "Test Directions";
    public static final long ID = 1L;
    public static final String NOTES = "Test Note";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    private RecipeMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new RecipeMapperImpl(
                new IngredientDescriptionMapperImpl(
                        new IngredientMapperImpl(),
                        new UnitOfMeasureMapperImpl()),
                new NoteMapperImpl(),
                new TimeMapper(),
                new CategoryMapperImpl()
        );
    }

    @Test
    public void toDto() {
        // Given
        Recipe recipe = createTestRecipe();

        // When
        RecipeDTO dto = mapper.toDto(recipe);

        // Then
        assertEquals(recipe.getId(), dto.getId());
        assertEquals(recipe.getDescription(), dto.getDescription());
        assertEquals(recipe.getPrepTime().toSecondOfDay() / 60, dto.getPrepTime());
        assertEquals(recipe.getCookTime().toSecondOfDay() / 60, dto.getCookTime());
        assertEquals(recipe.getServings(), dto.getServings());
        assertEquals(recipe.getSource(), dto.getSource());
        assertEquals(recipe.getUrl(), dto.getUrl());
        assertEquals(recipe.getDirections(), dto.getDirections());
        assertEquals(recipe.getDifficulty(), dto.getDifficulty());
        assertEquals(recipe.getNote().getId(), dto.getNote().getId());
        assertEquals(recipe.getIngredients().size(), dto.getIngredients().size());
        assertEquals(recipe.getCategories().size(), dto.getCategories().size());
    }

    @Test
    public void toDtoNullInput() {
        // When
        RecipeDTO dto = mapper.toDto(null);

        // Then
        assertNull(dto);
    }

    @Test
    public void fromDto() {
        // Given
        RecipeDTO dto = createRecipeDto();

        // When
        Recipe recipe = mapper.fromDto(dto);

        // Then
        assertEquals(dto.getId(), recipe.getId());
        assertEquals(dto.getDescription(), recipe.getDescription());
        assertEquals(dto.getPrepTime(), recipe.getPrepTime().toSecondOfDay() / 60);
        assertEquals(dto.getCookTime(), recipe.getCookTime().toSecondOfDay() / 60);
        assertEquals(dto.getServings(), recipe.getServings());
        assertEquals(dto.getSource(), recipe.getSource());
        assertEquals(dto.getUrl(), recipe.getUrl());
        assertEquals(dto.getDirections(), recipe.getDirections());
        assertEquals(dto.getDifficulty(), recipe.getDifficulty());
        assertEquals(dto.getNote().getId(), recipe.getNote().getId());
        assertEquals(dto.getIngredients().size(), recipe.getIngredients().size());
        assertEquals(dto.getCategories().size(), recipe.getCategories().size());
    }

    @Test
    public void fromDtoNullInput() {
        // When
        Recipe recipe = mapper.fromDto(null);

        // Then
        assertNull(recipe);
    }

    private Recipe createTestRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(ID);
        recipe.setDescription(DESCRIPTION);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setNote(new Note(ID, NOTES));

        IngredientDescription ingredientDescription = new IngredientDescription();
        ingredientDescription.setId(ID);
        recipe.addIngredientDescription(ingredientDescription);

        Category category = new Category();
        category.setId(ID);
        recipe.addCategory(category);

        return recipe;
    }

    private RecipeDTO createRecipeDto() {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(ID);
        dto.setDescription(DESCRIPTION);
        dto.setPrepTime(60);
        dto.setCookTime(30);
        dto.setServings(SERVINGS);
        dto.setSource(SOURCE);
        dto.setUrl(URL);
        dto.setDirections(DIRECTIONS);
        dto.setDifficulty(DIFFICULTY);
        dto.setNote(new NoteDTO(ID, NOTES));
        Set<IngredientDescriptionDTO> ingredientDTOSet = new HashSet<>();
        ingredientDTOSet.add(new IngredientDescriptionDTO());
        dto.setIngredients(ingredientDTOSet);
        Set<CategoryDTO> categoryDTOSet = new HashSet<>();
        categoryDTOSet.add(new CategoryDTO());
        dto.setCategories(categoryDTOSet);
        return dto;
    }
}