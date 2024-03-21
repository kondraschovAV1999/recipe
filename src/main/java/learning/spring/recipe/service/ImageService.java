package learning.spring.recipe.service;

import learning.spring.recipe.dto.RecipeDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(Long recipeId, MultipartFile file);
    void addImageFile(RecipeDTO recipeDTO, MultipartFile file);
}
