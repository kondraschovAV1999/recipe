package learning.spring.recipe.controllers;

import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/recipe")
@Controller
@AllArgsConstructor
@Slf4j
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping({"{id}/show/", "{id}/show"})
    public String showById(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/show";
    }

    @GetMapping("new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeDTO());
        return "recipe/recipeform";
    }

    @GetMapping("{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findDtoById(id));
        return "recipe/recipeform";
    }

    @PostMapping("/")
    public String saveOrUpdate(@ModelAttribute RecipeDTO dto) {
        RecipeDTO savedDto = recipeService.saveRecipeDto(dto);
        return "redirect:/recipe/%d/show".formatted(savedDto.getId());
    }

    @GetMapping("{id}/delete")
    public String deleteById(@PathVariable Long id) {

        log.debug("Deleting id: %d".formatted(id));
        recipeService.deleteById(id);
        return "redirect:/";
    }
}
