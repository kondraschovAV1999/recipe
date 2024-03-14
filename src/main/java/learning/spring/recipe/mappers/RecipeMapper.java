package learning.spring.recipe.mappers;

import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {IngredientDescriptionMapper.class, NoteMapper.class, TimeMapper.class, CategoryMapper.class})
public interface RecipeMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    RecipeDTO toDto(Recipe recipe);

    Recipe fromDto(RecipeDTO dto);
}
