package learning.spring.recipe.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
@Data
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    private String description;
    private LocalTime prepTime;
    private LocalTime cookTime;
    private Integer servings;
    private String source;
    private String url;
    @Lob
    private String directions;
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;
    @Lob
    private byte[] image;
    @OneToOne(cascade = CascadeType.ALL)
    private Note note; // owning side. The foreign key is stored in this table

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<IngredientDescription> ingredients = new HashSet<>(); // bidirectional mapping

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>(); // owner side


    public void setNote(Note note) {
        this.note = note;
        note.setRecipe(this);
    }

    /***
     * Add category to the set of categories and
     * add this recipe to the set of recipes of provided category
     * @param category
     */
    public void addCategory(Category category) {
        categories.add(category);
        category.getRecipes().add(this);
    }

    /***
     * Add current ingredientDescription to the set
     * and set a reference to this recipe in the provided ingredientDescription
     * @param ingredientDescription
     */
    public void addIngredientDescription(IngredientDescription ingredientDescription) {
        ingredients.add(ingredientDescription);
        ingredientDescription.setRecipe(this);
    }
}
