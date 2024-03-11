package learning.spring.recipe.controllers;

import learning.spring.recipe.repositories.CategoryRepository;
import learning.spring.recipe.repositories.UnitOfMeasureRepository;
import learning.spring.recipe.service.RecipeService;
import learning.spring.recipe.service.RecipeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {

        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {

        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
