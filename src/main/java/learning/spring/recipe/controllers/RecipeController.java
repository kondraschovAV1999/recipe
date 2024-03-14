package learning.spring.recipe.controllers;

import learning.spring.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/recipe")
@Controller
@AllArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    @RequestMapping({"/show/{id}"})
    public String showById(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/show";
    }
}
