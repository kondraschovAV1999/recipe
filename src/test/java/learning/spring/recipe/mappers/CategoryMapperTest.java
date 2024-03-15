package learning.spring.recipe.mappers;

import learning.spring.recipe.dto.CategoryDTO;
import learning.spring.recipe.model.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    public static final CategoryMapper mapper = CategoryMapper.INSTANCE;
    private final String description = "cat";
    private final Long id = 1L;

    @Test
    void toDto() {
        Category category = new Category(description);
        category.setId(id);
        CategoryDTO categoryDto = mapper.toDto(category);

        assertNotNull(categoryDto);
        assertEquals(description, categoryDto.getDescription());
        assertEquals(id, categoryDto.getId());
    }

    @Test
    void toDtoNullInput() {
        assertNull(mapper.toDto(null));
    }

    @Test
    void toDtoEmptyObject() {
        Category category = new Category();
        CategoryDTO categoryDto = mapper.toDto(category);

        assertNotNull(categoryDto);
        assertNull(categoryDto.getDescription());
        assertNull(categoryDto.getId());
    }

    @Test
    void fromDto() {
        CategoryDTO dto = new CategoryDTO(id, description);
        Category categoryFromDto = mapper.fromDto(dto);

        assertNotNull(categoryFromDto);
        assertEquals(description, categoryFromDto.getDescription());
        assertEquals(id, categoryFromDto.getId());
    }

    @Test
    void fromDtoNullInput() {
        assertNull(mapper.fromDto(null));
    }

    @Test
    void fromDtoEmptyObject() {
        CategoryDTO dto = new CategoryDTO();
        Category categoryFromDto = mapper.fromDto(dto);

        assertNotNull(categoryFromDto);
        assertNull(categoryFromDto.getDescription());
        assertNull(categoryFromDto.getId());
    }
}