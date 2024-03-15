package learning.spring.recipe.controllers;

import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/recipe")
@Controller
@AllArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @RequestMapping({"{id}/show/","{id}/show"})
    public String showById(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/show";
    }

    @RequestMapping("new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeDTO());
        return "recipe/recipeform";
    }

    @PostMapping("/")
    public String saveOrUpdate(@ModelAttribute RecipeDTO dto) {
        RecipeDTO savedDto = recipeService.saveRecipeDto(dto);
        return "redirect:/recipe/show/%d".formatted(savedDto.getId());
    }
}
