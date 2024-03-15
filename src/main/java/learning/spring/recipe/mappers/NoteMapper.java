package learning.spring.recipe.mappers;

import learning.spring.recipe.dto.NoteDTO;
import learning.spring.recipe.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NoteMapper {
    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    NoteDTO toDto(Note note);

    Note fromDto(NoteDTO dto);
}
