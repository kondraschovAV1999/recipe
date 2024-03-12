package learning.spring.recipe.controllers;

import learning.spring.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@AllArgsConstructor
public class IndexController {
    private final RecipeService recipeService;
    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Invoking getIndexPage");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
