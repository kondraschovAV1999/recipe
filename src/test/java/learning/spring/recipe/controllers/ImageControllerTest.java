package learning.spring.recipe.controllers;

import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.exceptions.NotFoundException;
import learning.spring.recipe.service.ImageService;
import learning.spring.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    private ImageService imageService;

    @Mock
    private RecipeService recipeService;
    @InjectMocks
    private ImageController controller;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void showUploadForm() throws Exception {
        //given
        Long recipeId = 1L;
        RecipeDTO dto = new RecipeDTO();
        dto.setId(recipeId);

        //when
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipeId"));
    }

    @Test
    void handleImagePost() throws Exception {
        //given
        Long recipeId = 1L;
        MockMultipartFile multipartFile =
                new MockMultipartFile(
                        "imagefile",
                        "testing.txt",
                        "text/plain",
                        "Spring Framework Guru".getBytes());

        //when
        imageService.saveImageFile(recipeId, multipartFile);

        //then
        mockMvc.perform(multipart("/recipe/%d/image"
                        .formatted(recipeId)).file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/%d/show".formatted(recipeId)));
    }

    @Test
    void handleImagePostNotFoundRecipe() throws Exception {

        //given
        Long recipeId = 1L;
        MockMultipartFile multipartFile =
                new MockMultipartFile(
                        "imagefile",
                        "testing.txt",
                        "text/plain",
                        "Spring Framework Guru".getBytes());

        //when
        doThrow(NotFoundException.class).when(imageService)
                .saveImageFile(anyLong(), any());


        mockMvc.perform(multipart("/recipe/%d/image"
                        .formatted(recipeId)).file(multipartFile))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));

    }

    @Test
    void renderImageFromDB() throws Exception {
        // given
        Long id = 1L;
        RecipeDTO dto = new RecipeDTO();
        dto.setId(id);
        String s = "fake image text";
        byte[] bytes = s.getBytes();
        dto.setImage(bytes);

        when(recipeService.findDtoById(anyLong())).thenReturn(dto);
        //when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/recipe/%d/recipeimage".formatted(id)))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(s.getBytes().length, responseBytes.length);
    }

    @Test
    void renderImageFromDBNotFoundRecipe() throws Exception {

        when(recipeService.findDtoById(anyLong()))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(
                        get("/recipe/%d/recipeimage".formatted(1L)))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }
}