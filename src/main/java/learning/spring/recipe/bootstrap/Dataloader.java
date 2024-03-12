package learning.spring.recipe.bootstrap;

import learning.spring.recipe.model.*;
import learning.spring.recipe.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class Dataloader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientRepository ingredientRepository;


    public Dataloader(RecipeRepository recipeRepository, CategoryRepository categoryRepository,
                      UnitOfMeasureRepository unitOfMeasureRepository,
                      IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientRepository = ingredientRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        /*---------------------------Guacamole-----------------------------*/
        String guacamoleDescription = "Guacamole";

        Set<Category> guacamoleCategories = new HashSet<>();
        guacamoleCategories.add(new Category("Snacks and Appetizers"));
        guacamoleCategories.add(new Category("Game Day"));
        guacamoleCategories.add(new Category("4th of July"));
        guacamoleCategories.add(new Category("Super Bowl"));

        LocalTime guacamolePerpTime = LocalTime.of(0, 10);
        LocalTime guacamoleCookTime = LocalTime.of(0, 10);
        int guacamoleServings = 2;

        String guacamoleUrl = "https://www.simplyrecipes.com/recipes/perfect_guacamole/#toc-ingredients-for-easy-guacamole";
        String guacamoleSource = "https://www.simplyrecipes.com/";
        String guacamoleDirections = """
                1. Cut the avocados:
                Cut the avocados in half.
                Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon.
                (See How to Cut and Peel an Avocado.) Place in a bowl.
                2. Mash the avocado flesh:
                Using a fork, roughly mash the avocado.
                (Don't overdo it! The guacamole should be a little chunky.)
                3. Add the remaining ingredients to taste:
                Sprinkle with salt and lime (or lemon) juice.
                The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.
                         
                Add the chopped onion, cilantro, black pepper, and chilis.
                Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.
                                
                Remember that much of this is done to taste because of the variability in the fresh ingredients.
                Start with this recipe and adjust to your taste.
                4. Serve immediately
                If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it.
                (The oxygen in the air causes oxidation which will turn the guacamole brown.)
                                
                Garnish with slices of red radish or jigama strips.
                Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.
                                
                Refrigerate leftover guacamole up to 3 days
                """;
        Difficulty guacamoleDifficulty = Difficulty.EASY;

        Path pathGuacamoleImage = Path.of("src/main/resources/static/images/guacamole.webp");

        String guacamoleNotes = "Be careful handling chilis! If using, it's best to wear food-safe gloves. " +
                                "If no gloves are available, wash your hands thoroughly after handling, " +
                                "and do not touch your eyes or the area near your eyes for several hours afterwards.";

        Set<IngredientDescription> guacamoleIngredientDescriptions = new HashSet<>();

        guacamoleIngredientDescriptions.add(createIngredientDescription(
                "avocado", "Item", "2"));
        guacamoleIngredientDescriptions.add(createIngredientDescription(
                "kosher salt", "Teaspoon", "0.25"));
        guacamoleIngredientDescriptions.add(createIngredientDescription(
                "lime", "Tablespoon", "1"));
        guacamoleIngredientDescriptions.add(createIngredientDescription(
                "red onion", "Tablespoon", "3"));
        guacamoleIngredientDescriptions.add(createIngredientDescription(
                "serrano chilly", "Item", "1"));
        guacamoleIngredientDescriptions.add(createIngredientDescription(
                "cilantro", "Tablespoon", "1"));
        guacamoleIngredientDescriptions.add(createIngredientDescription(
                "black pepper", "Pinch", "1"));
        guacamoleIngredientDescriptions.add(createIngredientDescription(
                "tomato", "Item", "1"));
        guacamoleIngredientDescriptions.add(createIngredientDescription(
                "radish", "Item", "1"));

        createRecipe(guacamoleDescription,
                guacamoleCategories,
                guacamolePerpTime,
                guacamoleCookTime,
                guacamoleServings,
                guacamoleUrl,
                guacamoleSource,
                guacamoleDirections,
                guacamoleDifficulty,
                pathGuacamoleImage,
                guacamoleNotes,
                guacamoleIngredientDescriptions);

        /*---------------------Spicy Grilled Chicken Tacos-------------------------*/
        String sgctDescription = "Spicy Grilled Chicken Tacos";

        Set<Category> sgctCategories = new HashSet<>();
        sgctCategories.add(new Category("Healthy Dinners"));
        sgctCategories.add(new Category("Grilled Chicken"));
        sgctCategories.add(new Category("Tacos"));
        sgctCategories.add(new Category("Super Bowl"));

        LocalTime sgctPerpTime = LocalTime.of(0, 20);
        LocalTime sgctCookTime = LocalTime.of(0, 15);
        int sgctServings = 5;

        String sgctUrl = "https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/";
        String sgctSource = "https://www.simplyrecipes.com/";

        String sgctDirections = """
                1. Prepare the grill:
                   Prepare either a gas or charcoal grill for medium-high, direct heat.
                2. Make the marinade and coat the chicken:
                   In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.
                   Set aside to marinate while the grill heats and you prepare the rest of the toppings.
                3. Grill the chicken:
                   Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165°F. Transfer to a plate and rest for 5 minutes.
                4. Thin the sour cream with milk:
                   Stir together the sour cream and milk to thin out the sour cream to make it easy to drizzle.
                5. Assemble the tacos:
                   Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.
                6. Warm the tortillas:
                   Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.
                   Wrap warmed tortillas in a tea towel to keep them warm until serving.
                """;

        Difficulty sgctDifficulty = Difficulty.MODERATE;

        Path pathSgctImage = Path.of("src/main/resources/static/images/spicy_grilled_chicken_tacos.webp");

        String sgctNotes = "Look for ancho chile powder with the Mexican ingredients at your grocery store, " +
                           "on buy it online. (If you can't find ancho chili powder, you replace the ancho " +
                           "chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, " +
                           "though the flavor won't be quite the same.)";

        Set<IngredientDescription> sgctIngredientDescriptions = new HashSet<>();

        sgctIngredientDescriptions.add(createIngredientDescription(
                "ancho chili powder", "Tablespoon", "2"));
        sgctIngredientDescriptions.add(createIngredientDescription(
                "oregano", "Teaspoon", "1"));
        sgctIngredientDescriptions.add(createIngredientDescription(
                "kosher salt", "Teaspoon", "0.5"));
        sgctIngredientDescriptions.add(createIngredientDescription(
                "sugar", "Teaspoon", "1"));
        sgctIngredientDescriptions.add(createIngredientDescription(
                "cumin", "Teaspoon", "1"));
        sgctIngredientDescriptions.add(createIngredientDescription(
                "clove garlic", "Item", "1"));
        sgctIngredientDescriptions.add(createIngredientDescription(
                "orange zest", "Tablespoon ", "1"));
        sgctIngredientDescriptions.add(createIngredientDescription(
                "orange juice", "Tablespoon ", "3"));
        sgctIngredientDescriptions.add(createIngredientDescription(
                "olive oil", "Tablespoon ", "2"));
        sgctIngredientDescriptions.add(createIngredientDescription(
                "chicken thighs", "Item ", "5"));

        createRecipe(sgctDescription,
                sgctCategories,
                sgctPerpTime,
                sgctCookTime,
                sgctServings,
                sgctUrl,
                sgctSource,
                sgctDirections,
                sgctDifficulty,
                pathSgctImage,
                sgctNotes,
                sgctIngredientDescriptions);
    }

    private IngredientDescription createIngredientDescription(String ingredientName, String UMO, String amount) {

        IngredientDescription ingredientDescription = new IngredientDescription();
        Ingredient ingredient = new Ingredient();
        ingredient.setDescription(ingredientName);
        ingredientRepository.save(ingredient);

        var uomOptional = unitOfMeasureRepository.findByDescription(UMO);
        UnitOfMeasure uom;

        if (uomOptional.isEmpty()) {
            uom = new UnitOfMeasure();
            uom.setDescription(UMO);
            unitOfMeasureRepository.save(uom);
        } else {
            uom = uomOptional.get();
        }

        ingredientDescription.setAmount(new BigDecimal(amount));
        ingredientDescription.setIngredient(ingredient);
        ingredientDescription.setUnitOfMeasure(uom);

        return ingredientDescription;
    }

    private Recipe createRecipe(String description, Set<Category> categories, LocalTime prepTime,
                                LocalTime cookTime, int servings, String url, String source,
                                String directions, Difficulty difficulty, Path path, String notes,
                                Set<IngredientDescription> ingredientDescriptions) {
        Recipe recipe = new Recipe();
        recipe.setDescription(description);
        categories.forEach(recipe::addCategory);
        categoryRepository.saveAll(categories); // HAVE TO DO THIS EXPLICITLY (cascade isn't used)
        recipe.setPrepTime(prepTime);
        recipe.setCookTime(cookTime);
        recipe.setServings(servings);
        recipe.setUrl(url);
        recipe.setSource(source);
        recipe.setDirections(directions);
        recipe.setDifficulty(difficulty);

        try {
            byte[] imageGuacamole = Files.readAllBytes(path);
            recipe.setImage(imageGuacamole);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Note note = new Note();
        note.setNotes(notes);
        recipe.setNote(note);
        ingredientDescriptions.forEach(recipe::addIngredientDescription);
        recipe.setIngredients(ingredientDescriptions);
        return recipeRepository.save(recipe);
    }
}
