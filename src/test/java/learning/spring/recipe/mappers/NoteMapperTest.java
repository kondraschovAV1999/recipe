package learning.spring.recipe.mappers;

import learning.spring.recipe.dto.NoteDTO;
import learning.spring.recipe.model.Note;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NoteMapperTest {
    private final NoteMapper mapper = NoteMapper.INSTANCE;

    @Test
    public void toDto() {
        // Given
        Note note = new Note(1L, "Test Notes");

        // When
        NoteDTO dto = mapper.toDto(note);

        // Then
        assertEquals(note.getId(), dto.getId());
        assertEquals(note.getNotes(), dto.getNotes());
    }

    @Test
    public void toDtoEmptyObject() {
        // Given
        Note note = new Note();

        // When
        NoteDTO dto = mapper.toDto(note);

        // Then
        assertNull(dto.getId());
        assertNull(dto.getNotes());
    }

    @Test
    public void toDtoNullInput() {
        // When
        NoteDTO dto = mapper.toDto(null);

        // Then
        assertNull(dto);
    }

    @Test
    public void fromDto() {
        // Given
        NoteDTO dto = new NoteDTO(1L, "Test Notes");

        // When
        Note note = mapper.fromDto(dto);

        // Then
        assertEquals(dto.getId(), note.getId());
        assertEquals(dto.getNotes(), note.getNotes());
    }

    @Test
    public void fromDtoEmptyObject() {
        // Given
        NoteDTO dto = new NoteDTO();

        // When
        Note note = mapper.fromDto(dto);

        // Then
        assertNull(note.getId());
        assertNull(note.getNotes());
    }

    @Test
    public void fromDtoNullInput() {
        // When
        Note note = mapper.fromDto(null);

        // Then
        assertNull(note);
    }

}