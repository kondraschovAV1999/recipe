package learning.spring.recipe.mappers;

import learning.spring.recipe.dto.UnitOfMeasureDTO;
import learning.spring.recipe.model.UnitOfMeasure;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UnitOfMeasureMapperTest {

    private final UnitOfMeasureMapper mapper = UnitOfMeasureMapper.INSTANCE;

    @Test
    public void toDto() {
        // Given
        UnitOfMeasure uom = new UnitOfMeasure(1L, "Test Description");

        // When
        UnitOfMeasureDTO dto = mapper.toDto(uom);

        // Then
        assertEquals(uom.getId(), dto.getId());
        assertEquals(uom.getDescription(), dto.getDescription());
    }

    @Test
    public void toDtoEmptyObject() {
        // Given
        UnitOfMeasure uom = new UnitOfMeasure();

        // When
        UnitOfMeasureDTO dto = mapper.toDto(uom);

        // Then
        assertNull(dto.getId());
        assertNull(dto.getDescription());
    }

    @Test
    public void toDtoNullInput() {
        // When
        UnitOfMeasureDTO dto = mapper.toDto(null);

        // Then
        assertNull(dto);
    }

    @Test
    public void fromDto() {
        // Given
        UnitOfMeasureDTO dto = new UnitOfMeasureDTO(1L, "Test Description");

        // When
        UnitOfMeasure uom = mapper.fromDto(dto);

        // Then
        assertEquals(dto.getId(), uom.getId());
        assertEquals(dto.getDescription(), uom.getDescription());
    }

    @Test
    public void fromDtoEmptyObject() {
        // Given
        UnitOfMeasureDTO dto = new UnitOfMeasureDTO();

        // When
        UnitOfMeasure uom = mapper.fromDto(dto);

        // Then
        assertNull(uom.getId());
        assertNull(uom.getDescription());
    }

    @Test
    public void fromDtoNullInput() {
        // When
        UnitOfMeasure uom = mapper.fromDto(null);

        // Then
        assertNull(uom);
    }

}