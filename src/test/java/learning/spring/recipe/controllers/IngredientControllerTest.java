package learning.spring.recipe.controllers;


import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.service.IngredientService;
import learning.spring.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @InjectMocks
    IngredientController controller;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception {
//        controller = new IngredientController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testListIngredients() throws Exception {
        //given
        RecipeDTO dto = new RecipeDTO();
        when(recipeService.findDtoById(anyLong())).thenReturn(dto);
        Long id = 1L;

        //when
        mockMvc.perform(get("/recipe/%d/ingredients".formatted(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        //then
        verify(recipeService).findDtoById(anyLong());
    }

    @Test
    public void testShowIngredient() throws Exception {
        // given
        IngredientDescriptionDTO dto = new IngredientDescriptionDTO();
        Long recipeId = 1L;
        Long ingredientId = 2L;

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(dto);

        //then
        mockMvc.perform(get(
                        "/recipe/%d/ingredient/%d/show".formatted(recipeId, ingredientId)))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredientDesc"));
    }
}
