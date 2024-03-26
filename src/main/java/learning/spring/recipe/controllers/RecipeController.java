package learning.spring.recipe.controllers;

import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.exceptions.NotFoundException;
import learning.spring.recipe.service.ImageService;
import learning.spring.recipe.service.RecipeService;
import learning.spring.recipe.service.UnitOfMeasureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@RequestMapping("/recipe")
@Controller
@AllArgsConstructor
@Slf4j
public class RecipeController {
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;
    private final ImageService imageService;

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
                                   @RequestParam("deleteIngredient") Integer index,
                                   Model model) {
        log.debug("Deleting ingredientDesc from the recipe");
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

    @PostMapping(value = "/", params = "addImage")
    public String uploadImageNewRecipe(@ModelAttribute("recipe") RecipeDTO dto,
                                       @RequestParam("imagefile") MultipartFile file,
                                       Model model) {
        imageService.addImageFile(dto, file);
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/recipeform";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception e) {
        log.error("Handling not found exception");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", e);
        return modelAndView;
    }

}
