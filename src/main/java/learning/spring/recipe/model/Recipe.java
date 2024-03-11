package learning.spring.recipe.model;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(LocalTime prepTime) {
        this.prepTime = prepTime;
    }

    public LocalTime getCookTime() {
        return cookTime;
    }

    public void setCookTime(LocalTime cookTime) {
        this.cookTime = cookTime;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
        note.setRecipe(this);
    }

    public Set<IngredientDescription> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<IngredientDescription> ingredients) {
        this.ingredients = ingredients;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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
