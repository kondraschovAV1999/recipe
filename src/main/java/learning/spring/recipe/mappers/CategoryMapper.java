package learning.spring.recipe.mappers;

import learning.spring.recipe.dto.CategoryDTO;
import learning.spring.recipe.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category fromDto(CategoryDTO dto);
    CategoryDTO toDto(Category category);
}
