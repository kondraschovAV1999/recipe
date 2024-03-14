package learning.spring.recipe.mappers;

import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.model.IngredientDescription;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {IngredientMapper.class, UnitOfMeasureMapper.class})
public interface IngredientDescriptionMapper {
    IngredientDescriptionMapper INSTANCE = Mappers.getMapper(IngredientDescriptionMapper.class);

    IngredientDescriptionDTO toDto(IngredientDescription IngredientDescription);
    IngredientDescription fromDto(IngredientDescriptionDTO dto);
}
