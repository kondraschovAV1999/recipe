package learning.spring.recipe.service;

import learning.spring.recipe.dto.RecipeDTO;
import learning.spring.recipe.exceptions.NotFoundException;
import learning.spring.recipe.model.Recipe;
import learning.spring.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    void saveImageFile() throws Exception {
        //given
        Long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);
        MockMultipartFile multipartFile =
                new MockMultipartFile(
                        "imagefile",
                        "testing.txt",
                        "text/plain",
                        "Spring Framework Guru".getBytes());


        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        ArgumentCaptor<Recipe> captor = ArgumentCaptor.forClass(Recipe.class);
        //when
        imageService.saveImageFile(id, multipartFile);

        //then
        verify(recipeRepository).save(captor.capture());
        Recipe savedRecipe = captor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }

    @Test
    void saveImageFileWithNullRecipe() {
        assertThrows(NotFoundException.class, () ->
                imageService.saveImageFile(1L, null));

    }

    @Test
    void addImageFile() throws Exception {
        //given
        Long id = 1L;
        RecipeDTO dto = new RecipeDTO();
        dto.setId(id);
        MockMultipartFile multipartFile =
                new MockMultipartFile(
                        "imagefile",
                        "testing.txt",
                        "text/plain",
                        "Spring Framework Guru".getBytes());


        //when
        imageService.addImageFile(dto, multipartFile);

        //then
        assertEquals(multipartFile.getBytes().length, dto.getImage().length);
    }
}