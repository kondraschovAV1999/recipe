package learning.spring.recipe.mappers;

import learning.spring.recipe.dto.UnitOfMeasureDTO;
import learning.spring.recipe.model.UnitOfMeasure;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UnitOfMeasureMapper {
    UnitOfMeasureMapper INSTANCE = Mappers.getMapper(UnitOfMeasureMapper.class);

    UnitOfMeasureDTO toDto(UnitOfMeasure uom);

    UnitOfMeasure fromDto(UnitOfMeasureDTO dto);
}
