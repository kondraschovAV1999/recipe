package learning.spring.recipe.controllers;


import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.service.IngredientService;
import learning.spring.recipe.service.RecipeService;
import learning.spring.recipe.service.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class IngredientControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    @InjectMocks
    private IngredientController controller;
    private MockMvc mockMvc;

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

    @Test
    void testUpdateIngredient() throws Exception {
        //given
        IngredientDescriptionDTO dto = new IngredientDescriptionDTO();

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong()))
                .thenReturn(dto);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/%d/ingredient/%d/update".formatted(1, 2)))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredientDesc"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    void testSaveOrUpdate() throws Exception {
        //given
        Long ingredientDescId = 3L;
        Long recipeId = 2L;
        IngredientDescriptionDTO dto = new IngredientDescriptionDTO();
        dto.setId(ingredientDescId);
        dto.setRecipeId(recipeId);

        //when
        when(ingredientService.saveIngredientDto(any())).thenReturn(dto);

        //then
        mockMvc.perform(post("/recipe/%d/ingredient".formatted(recipeId))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description", "some string"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/%d/ingredient/%d/show"
                        .formatted(recipeId, ingredientDescId)));
    }
}
