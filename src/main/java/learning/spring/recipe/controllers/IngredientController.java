package learning.spring.recipe.controllers;

import learning.spring.recipe.service.IngredientService;
import learning.spring.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/recipe/{recipeId}")
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @GetMapping({"ingredients"})
    public String listIngredients(@PathVariable Long recipeId, Model model) {
        log.debug("Getting ingredient list for recipe id: %d".formatted(recipeId));

        model.addAttribute("recipe", recipeService.findDtoById(recipeId));
        return "recipe/ingredient/list";
    }

    @GetMapping("ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable Long recipeId,
                                       @PathVariable Long ingredientId, Model model) {
        model.addAttribute("ingredientDesc",
                ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
        return "recipe/ingredient/show";
    }
}
