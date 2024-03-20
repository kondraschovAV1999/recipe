package learning.spring.recipe.controllers;

import jakarta.servlet.http.HttpServletRequest;
import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.service.RecipeService;
import learning.spring.recipe.service.UnitOfMeasureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/recipe")
@Controller
@AllArgsConstructor
@Slf4j
public class RecipeController {
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

    @GetMapping({"{id}/show/", "{id}/show"})
    public String showById(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/show";
    }

    @GetMapping("new")
    public String newRecipe(Model model,
                            @ModelAttribute(name = "recipe") RecipeDTO recipe) {
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/recipeform";
    }

    @PostMapping(value = "/", params = {"addIngredient"})
    public String addIngredient(@ModelAttribute(name = "recipe") RecipeDTO recipe, Model model) {

        if (recipe != null) {
            if (recipe.getIngredients() == null) {
                recipe.setIngredients(new ArrayList<>());
            }
            recipe.getIngredients().add(new IngredientDescriptionDTO());
        }

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/recipeform";
    }

    @PostMapping(value = "/", params = {"deleteIngredient"})
    public String deleteIngredient(@ModelAttribute(name = "recipe") RecipeDTO recipe,
                                   HttpServletRequest req,
                                   Model model) {
        log.debug("Deleting ingredientDesc from the recipe");
        int index = Integer.parseInt(req.getParameter("deleteIngredient"));
        recipeService.deleteIngredient(index, recipe);

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/recipeform";
    }

    @GetMapping("{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findDtoById(id));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
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
