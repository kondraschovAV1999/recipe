package learning.spring.recipe.mappers;

import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.model.Recipe;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {IngredientDescriptionMapper.class, NoteMapper.class, TimeMapper.class, CategoryMapper.class},
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RecipeMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    RecipeDTO toDto(Recipe recipe);

    Recipe fromDto(RecipeDTO dto);
}
