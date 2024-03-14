package learning.spring.recipe.mappers;

import learning.spring.recipe.dto.IngredientDTO;
import learning.spring.recipe.model.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IngredientMapper {
    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    IngredientDTO toDto(Ingredient ingredient);

    Ingredient fromDto(IngredientDTO dto);
}
