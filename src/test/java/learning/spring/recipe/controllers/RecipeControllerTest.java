package learning.spring.recipe.controllers;

import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.model.Recipe;
import learning.spring.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {
    @Mock
    private RecipeService recipeService;
    @InjectMocks
    private RecipeController recipeController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void showById() throws Exception {
        Long id = 1L;

        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/%d/show".formatted(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testGetNewRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testPostNewRecipeFrom() throws Exception {
        Long id = 2L;
        RecipeDTO dto = new RecipeDTO();
        dto.setId(id);

        when(recipeService.saveRecipeDto(any())).thenReturn(dto);

        mockMvc.perform(post("/recipe/")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description", "some string"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/show/%d".formatted(id)));
    }

    @Test
    void testGetUpdateView() throws Exception {
        Long id = 2L;
        RecipeDTO dto = new RecipeDTO();
        dto.setId(id);

        when(recipeService.findDtoById(anyLong())).thenReturn(dto);

        mockMvc.perform(get("/recipe/%d/update".formatted(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }
}