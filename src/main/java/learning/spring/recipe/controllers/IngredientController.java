package learning.spring.recipe.controllers;

import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.service.IngredientService;
import learning.spring.recipe.service.RecipeService;
import learning.spring.recipe.service.UnitOfMeasureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/recipe/{recipeId}")
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

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

    @GetMapping("ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable Long recipeId,
                                   @PathVariable Long ingredientId, Model model) {
        model.addAttribute("ingredientDesc",
                ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientDescriptionDTO dto) {
        IngredientDescriptionDTO savedDto = ingredientService.saveIngredientDto(dto);

        log.debug("saved recipe id:" + savedDto.getRecipeId());
        log.debug("saved ingredient id:" + savedDto.getId());

        return "redirect:/recipe/%d/ingredient/%d/show"
                .formatted(savedDto.getRecipeId(), savedDto.getId());
    }
}
