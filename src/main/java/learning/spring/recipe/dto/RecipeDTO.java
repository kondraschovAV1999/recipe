package learning.spring.recipe.dto;

import learning.spring.recipe.model.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;
    private NoteDTO note;
    private Set<IngredientDescriptionDTO> ingredients = new HashSet<>();
    private Set<CategoryDTO> categories = new HashSet<>();
}
