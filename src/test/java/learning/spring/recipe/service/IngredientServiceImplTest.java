package learning.spring.recipe.service;

import learning.spring.recipe.dto.IngredientDTO;
import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.dto.UnitOfMeasureDTO;
import learning.spring.recipe.exceptions.NotFoundException;
import learning.spring.recipe.mappers.*;
import learning.spring.recipe.model.Ingredient;
import learning.spring.recipe.model.IngredientDescription;
import learning.spring.recipe.model.Recipe;
import learning.spring.recipe.model.UnitOfMeasure;
import learning.spring.recipe.repositories.IngredientDescriptionRepository;
import learning.spring.recipe.repositories.IngredientRepository;
import learning.spring.recipe.repositories.RecipeRepository;
import learning.spring.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class IngredientServiceImplTest {
    private IngredientDescriptionMapper mapper;

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private IngredientDescriptionRepository ingredientDescriptionRepository;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private IngredientService ingredientService;

    private final Long ingredientDescId = 1L;
    private final Long ingredientId = 1L;
    private final String ingredientDesc = "description";
    private final Long uomId = 1L;

    @BeforeEach
    void setUp() {

        mapper = new IngredientDescriptionMapperImpl(
                new IngredientMapperImpl(),
                new UnitOfMeasureMapperImpl(),
                new RecipeToIdMapper(recipeRepository)
        );

        ingredientService = new IngredientServiceImpl(
                mapper,
                ingredientDescriptionRepository,
                unitOfMeasureRepository,
                ingredientRepository,
                recipeRepository
        );
    }

    @Test
    public void findByRecipeIdAndRecipeIdHappyPath() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        IngredientDescription ingredientDesc1 = new IngredientDescription();
        ingredientDesc1.setId(1L);
        ingredientDesc1.setAmount(new BigDecimal(1));

        IngredientDescription ingredientDesc2 = new IngredientDescription();
        ingredientDesc2.setId(2L);
        ingredientDesc2.setAmount(new BigDecimal(2));

        IngredientDescription ingredientDesc3 = new IngredientDescription();
        ingredientDesc3.setId(3L);
        ingredientDesc3.setAmount(new BigDecimal(3));


        recipe.addIngredientDescription(ingredientDesc1);
        recipe.addIngredientDescription(ingredientDesc2);
        recipe.addIngredientDescription(ingredientDesc3);
        Optional<IngredientDescription> ingredientDescriptionOptional = Optional.of(ingredientDesc3);

        when(ingredientDescriptionRepository.findByIdAndRecipeId(
                anyLong(), anyLong())).thenReturn(ingredientDescriptionOptional);

        //then
        IngredientDescriptionDTO dto = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //when
        assertEquals(3L, dto.getId());
        assertEquals(1L, dto.getRecipeId());
        verify(ingredientDescriptionRepository).findByIdAndRecipeId(
                anyLong(), anyLong());
    }

    @Test
    void findByRecipeIdAndRecipeIdNotFoundRecipe() {
        assertThrows(NotFoundException.class, () ->
                ingredientService.findByRecipeIdAndIngredientId(1L, 1L));
    }

    @Test
    void testSaveIngredientDto() {
        //given
        Long recipeId = 2L;

        IngredientDescription ingredientDescription = createIngredientDesc(ingredientDescId);

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.addIngredientDescription(ingredientDescription);

        when(ingredientDescriptionRepository.findById(anyLong())).thenReturn(
                Optional.of(ingredientDescription));
        when(ingredientRepository.findByDescription(anyString())).thenReturn(
                Optional.of(ingredientDescription.getIngredient()));
        when(unitOfMeasureRepository.findById(anyLong())).thenReturn(
                Optional.of(ingredientDescription.getUnitOfMeasure()));
        when(ingredientDescriptionRepository.save(any())).thenReturn(
                ingredientDescription);
        when(recipeRepository.findById(anyLong())).thenReturn(
                Optional.of(recipe));

        //when
        IngredientDescriptionDTO savedDto = ingredientService.saveIngredientDto(
                mapper.toDto(ingredientDescription));

        //then
        assertEquals(ingredientDescId, savedDto.getId());
        assertEquals(recipeId, savedDto.getRecipeId());
        assertEquals(ingredientId, savedDto.getIngredient().getId());
        assertEquals(uomId, savedDto.getUom().getId());
        verify(ingredientDescriptionRepository).findById(anyLong());
        verify(ingredientRepository).findByDescription(anyString());
        verify(unitOfMeasureRepository).findById(anyLong());
        verify(ingredientDescriptionRepository).save(any(IngredientDescription.class));
        verify(recipeRepository).findById(anyLong());
    }

    @Test
    void testSaveIngredientDtoWithNullRecipeId() {
        assertThrows(NotFoundException.class, () ->
                ingredientService.saveIngredientDto(new IngredientDescriptionDTO()));
    }

    @Test
    void testDeleteByIdAndRecipeId() {
        //given
        Long id = 1L;
        IngredientDescription ingredientDescription = new IngredientDescription();
        ingredientDescription.setId(id);

        Long recipeId = 2L;
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.addIngredientDescription(ingredientDescription);


        when(recipeRepository.findById(anyLong())).thenReturn(
                Optional.of(recipe));
        when(ingredientDescriptionRepository.findById(anyLong())).thenReturn(
                Optional.of(ingredientDescription));

        //when
        ingredientService.deleteByIdAndRecipeId(id, recipeId);

        //then
        assertEquals(0, recipe.getIngredients().size());
        assertNull(ingredientDescription.getRecipe());

        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository).save(any(Recipe.class));
        verify(ingredientDescriptionRepository).deleteById(anyLong());
    }

    @Test
    void testDeleteByIdAndRecipeIdWithNullRecipeId() {
        assertThrows(NotFoundException.class, () ->
                ingredientService.deleteByIdAndRecipeId(1L, 1L));
    }

    @Test
    void testFindById() {
        //given
        Long id = 1L;
        IngredientDescription ingredientDescription = new IngredientDescription();
        ingredientDescription.setId(id);

        when(ingredientDescriptionRepository.findById(anyLong()))
                .thenReturn(Optional.of(ingredientDescription));

        //when
        var returnedIngredientDesc = ingredientService.findById(id);

        //then
        assertNotNull(returnedIngredientDesc);
        assertEquals(id, returnedIngredientDesc.getId());
        verify(ingredientDescriptionRepository).findById(anyLong());
    }

    @Test
    void testFindByIdNotFound() {
        assertThrows(NotFoundException.class,
                () -> ingredientService.findById(1));
    }

    @Test
    void testCreateNewIngredientDescFromForm() {
        //given
        IngredientDescriptionDTO dto = createIngredientDescDto(null);
        IngredientDescription ingredientDescription = createIngredientDesc(null);

        when(ingredientRepository.findByDescription(anyString()))
                .thenReturn(Optional.of(ingredientDescription.getIngredient()));

        when(unitOfMeasureRepository.findById(anyLong()))
                .thenReturn(Optional.of(ingredientDescription.getUnitOfMeasure()));

        //when
        var returnedIngredientDesc = ingredientService
                .createIngredientDescFromForm(dto);

        //then
        assertNotNull(returnedIngredientDesc);
        assertNotNull(returnedIngredientDesc.getIngredient());
        assertNotNull(returnedIngredientDesc.getUnitOfMeasure());
        assertEquals(ingredientId, returnedIngredientDesc.getIngredient().getId());
        assertEquals(ingredientDesc,
                returnedIngredientDesc.getIngredient().getDescription());
        assertEquals(uomId, returnedIngredientDesc.getUnitOfMeasure().getId());
        verify(ingredientRepository).findByDescription(anyString());
        verify(unitOfMeasureRepository).findById(anyLong());
    }

    @Test
    void testCreateNewIngredientDescFromFormUomNotFound() {
        //given
        IngredientDescriptionDTO dto = createIngredientDescDto(null);
        IngredientDescription ingredientDescription = createIngredientDesc(null);

        //when
        when(ingredientRepository.findByDescription(anyString()))
                .thenReturn(Optional.of(ingredientDescription.getIngredient()));

        when(unitOfMeasureRepository.findById(anyLong()))
                .thenThrow(NotFoundException.class);

        //then
        assertThrows(NotFoundException.class,
                () -> ingredientService.createIngredientDescFromForm(dto));
    }

    @Test
    void testCreateIngredientDescFromFormUpdate() {
        //given
        IngredientDescriptionDTO dto = createIngredientDescDto(ingredientDescId);
        IngredientDescription ingredientDescription = createIngredientDesc(ingredientDescId);

        when(ingredientDescriptionRepository.findById(anyLong()))
                .thenReturn(Optional.of(ingredientDescription));

        when(ingredientRepository.findByDescription(anyString()))
                .thenReturn(Optional.of(ingredientDescription.getIngredient()));

        when(unitOfMeasureRepository.findById(anyLong()))
                .thenReturn(Optional.of(ingredientDescription.getUnitOfMeasure()));

        //when
        var returnedIngredientDesc = ingredientService
                .createIngredientDescFromForm(dto);

        //then
        assertNotNull(returnedIngredientDesc);
        assertNotNull(returnedIngredientDesc.getIngredient());
        assertNotNull(returnedIngredientDesc.getUnitOfMeasure());
        assertEquals(ingredientId, returnedIngredientDesc.getIngredient().getId());
        assertEquals(ingredientDesc,
                returnedIngredientDesc.getIngredient().getDescription());
        assertEquals(uomId, returnedIngredientDesc.getUnitOfMeasure().getId());
        assertEquals(ingredientDescId, returnedIngredientDesc.getId());
        verify(ingredientRepository).findByDescription(anyString());
        verify(unitOfMeasureRepository).findById(anyLong());
        verify(ingredientDescriptionRepository).findById(anyLong());
    }

    @Test
    void testCreateIngredientDescFromFormUpdateUomNotFound() {
        //given
        IngredientDescriptionDTO dto = createIngredientDescDto(ingredientDescId);
        IngredientDescription ingredientDescription = createIngredientDesc(ingredientDescId);

        //when
        when(ingredientDescriptionRepository.findById(anyLong()))
                .thenReturn(Optional.of(ingredientDescription));

        when(ingredientRepository.findByDescription(anyString()))
                .thenReturn(Optional.of(ingredientDescription.getIngredient()));

        when(unitOfMeasureRepository.findById(anyLong()))
                .thenThrow(NotFoundException.class);

        //then
        assertThrows(NotFoundException.class,
                () -> ingredientService.createIngredientDescFromForm(dto));
    }

    private IngredientDescription createIngredientDesc(Long ingredientDescId) {
        IngredientDescription ingredientDescription = new IngredientDescription();
        ingredientDescription.setId(ingredientDescId);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        ingredient.setDescription(ingredientDesc);
        ingredientDescription.setIngredient(ingredient);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(uomId);
        ingredientDescription.setUnitOfMeasure(uom);

        return ingredientDescription;
    }

    private IngredientDescriptionDTO createIngredientDescDto(Long ingredientDescId) {
        IngredientDescriptionDTO dto = new IngredientDescriptionDTO();
        dto.setId(ingredientDescId);

        IngredientDTO ingredientDto = new IngredientDTO();
        ingredientDto.setDescription(ingredientDesc);
        ingredientDto.setId(ingredientId);
        dto.setIngredient(ingredientDto);

        UnitOfMeasureDTO uomDto = new UnitOfMeasureDTO();
        uomDto.setId(uomId);
        dto.setUom(uomDto);

        return dto;
    }
}