package learning.spring.recipe.controllers;

import jakarta.servlet.http.HttpServletResponse;
import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.service.ImageService;
import learning.spring.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@AllArgsConstructor
@Slf4j
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    @GetMapping("recipe/{recipeId}/image")
    public String showUploadForm(@PathVariable Long recipeId, Model model) {
        model.addAttribute("recipeId", recipeId);
        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable Long recipeId,
                                  @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(recipeId, file);
        return "redirect:/recipe/%d/show".formatted(recipeId);
    }

    @GetMapping("recipe/{recipeId}/recipeimage")
    public void renderImageFromDB(@PathVariable Long recipeId, HttpServletResponse response) {
        RecipeDTO dto = recipeService.findDtoById(recipeId);

        response.setContentType("image/jpeg");

        if (dto.getImage() == null) return;

        try (InputStream is = new ByteArrayInputStream(dto.getImage())) {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
