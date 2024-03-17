package learning.spring.recipe.mappers;

import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.model.IngredientDescription;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {IngredientMapper.class, UnitOfMeasureMapper.class, RecipeToIdMapper.class},
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IngredientDescriptionMapper {
    IngredientDescriptionMapper INSTANCE = Mappers.getMapper(IngredientDescriptionMapper.class);

    @Mapping(source="unitOfMeasure", target = "uom")
    @Mapping(source="recipe", target = "recipeId")
    IngredientDescriptionDTO toDto(IngredientDescription ingredientDescription);
    @InheritInverseConfiguration
    IngredientDescription fromDto(IngredientDescriptionDTO dto);
}
