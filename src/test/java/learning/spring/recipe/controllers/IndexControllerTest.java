package learning.spring.recipe.controllers;

import learning.spring.recipe.model.Recipe;
import learning.spring.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {
    @InjectMocks
    IndexController indexController;
    @Mock
    Model model;
    @Mock
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getIndexPage() {

        // given
        List<Recipe> recipesData = new ArrayList<>();
        recipesData.add(new Recipe());
        recipesData.add(new Recipe());
        when(recipeService.getRecipes()).thenReturn(recipesData);


        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.captor();

        //when
        String viewName = indexController.getIndexPage(model);

        // then
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1))
                .addAttribute(eq("recipes"), argumentCaptor.capture());

        assertEquals("index", viewName);
        assertEquals(2, argumentCaptor.getValue().size());
    }
}