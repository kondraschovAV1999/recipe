package learning.spring.recipe.controllers;

import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.exceptions.NotFoundException;
import learning.spring.recipe.model.Recipe;
import learning.spring.recipe.service.ImageService;
import learning.spring.recipe.service.RecipeService;
import learning.spring.recipe.service.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {
    @Mock
    private RecipeService recipeService;
    @Mock
    private ImageService imageService;
    @Mock
    private UnitOfMeasureService unitOfMeasureService;
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
                .andExpect(view().name("redirect:/recipe/%d/show".formatted(id)));
    }

    @Test
    void testGetRecipeNotFound() throws Exception {

        when(recipeService.findById(anyLong()))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/%d/show".formatted(1)))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    void testGetUpdateView() throws Exception {
        Long id = 2L;
        RecipeDTO dto = new RecipeDTO();
        dto.setId(id);

        when(recipeService.findDtoById(anyLong())).thenReturn(dto);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/%d/update".formatted(id)))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testDeleteAction() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/recipe/%d/delete".formatted(id)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService).deleteById(anyLong());
    }

    @Test
    void testUploadImageNewRecipe() throws Exception {
        Long recipeId = 1L;
        RecipeDTO dto = new RecipeDTO();
        dto.setId(recipeId);
        MockMultipartFile multipartFile =
                new MockMultipartFile(
                        "imagefile",
                        "testing.txt",
                        "text/plain",
                        "Spring Framework Guru".getBytes());

        //when
        imageService.addImageFile(dto, multipartFile);

        //then
        mockMvc.perform(multipart("/recipe/").file(multipartFile)
                        .param("addImage", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeExists("uomList"));
    }
}