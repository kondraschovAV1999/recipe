package learning.spring.recipe.service;

import jakarta.transaction.Transactional;
import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.model.Recipe;
import learning.spring.recipe.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {
    private final RecipeRepository recipeRepository;

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {
        log.debug("Received a file");
        try {
            Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(
                    () -> {
                        String message = "Recipe with id=%d Not Found".formatted(recipeId);
                        log.error(message);
                        return new RuntimeException(message);
                    }
            );
            recipe.setImage(file.getBytes());
            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Error occurred", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addImageFile(RecipeDTO recipeDTO, MultipartFile file) {
        try {
            recipeDTO.setImage(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
