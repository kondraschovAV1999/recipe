package learning.spring.recipe.service;

import jakarta.transaction.Transactional;
import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.mappers.IngredientDescriptionMapper;
import learning.spring.recipe.model.IngredientDescription;
import learning.spring.recipe.model.Recipe;
import learning.spring.recipe.repositories.IngredientDescriptionRepository;
import learning.spring.recipe.repositories.IngredientRepository;
import learning.spring.recipe.repositories.RecipeRepository;
import learning.spring.recipe.repositories.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientDescriptionMapper ingredientDescriptionMapper;
    private final IngredientDescriptionRepository ingredientDescriptionRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public IngredientDescription findById(long id) {
        return ingredientDescriptionRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "IngredientDesc Not Found By id:%d"
                            .formatted(id);
                    log.error(message);
                    return new RuntimeException(message);
                });
    }

    @Override
    public IngredientDescriptionDTO findByRecipeIdAndIngredientId(long recipeId, long ingredientId) {

        return ingredientDescriptionMapper.toDto(
                ingredientDescriptionRepository.findByIdAndRecipeId(ingredientId, recipeId)
                        .orElseThrow(() -> {
                            String message = "Ingredient Not Found by ingredientId:%d and recipeId:%d"
                                    .formatted(ingredientId, recipeId);
                            log.error(message);
                            return new RuntimeException(message);
                        }));
    }

    @Override
    @Transactional
    public IngredientDescriptionDTO saveIngredientDto(IngredientDescriptionDTO dto) {

        if (dto.getRecipeId() == null) {
            log.error("Trying to save ingredientDescription but recipeId is null, id=%d"
                    .formatted(dto.getId()));
            throw new RuntimeException("Trying to update ingredientDescription but recipeId is null");
        }

        return ingredientDescriptionMapper.toDto(ingredientDescriptionRepository.
                save(createIngredientDescFromForm(dto)));
    }

    @Override
    @Transactional
    public IngredientDescription createIngredientDescFromForm(IngredientDescriptionDTO dto) {

        IngredientDescription ingredientDescFromDto = ingredientDescriptionMapper.fromDto(dto);

        IngredientDescription ingredientDescription = dto.getId() == null ?
                new IngredientDescription() :
                ingredientDescriptionRepository.findById(dto.getId()).orElseThrow(
                        () -> {
                            String message = "IngredientDescription NOT FOUND BY id=%d (recipeId=%d)"
                                    .formatted(dto.getId(), dto.getRecipeId());
                            log.error(message);
                            return new RuntimeException(message);
                        });

        ingredientDescription.setIngredient(
                ingredientRepository.findByDescription(dto.getIngredient().getDescription().trim())
                        .orElseGet(() -> ingredientRepository.save(
                                ingredientDescFromDto.getIngredient())
                        ));

        ingredientDescription.setUnitOfMeasure(
                unitOfMeasureRepository.findById(dto.getUom().getId())
                        .orElseThrow(() -> {
                            String message = "UOM with desc:%s and id:%d NOT FOUND"
                                    .formatted(dto.getUom().getDescription(), dto.getUom().getId());
                            log.error(message);
                            return new RuntimeException(message);
                        }));
        ingredientDescription.setAmount(ingredientDescFromDto.getAmount());
        ingredientDescription.setRecipe(ingredientDescFromDto.getRecipe());
        return ingredientDescription;
    }

    @Override
    public void deleteById(Long id) {
        ingredientDescriptionRepository.deleteById(id);
    }

    @Override
    public void deleteByIdAndRecipeId(Long id, Long recipeId) {
        log.debug("Deleting IngredientDesc with id=%d within recipe with id=%d"
                .formatted(id, recipeId));

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(
                () -> {
                    String message = "Recipe with id=%d Not Found".formatted(recipeId);
                    log.error(message);
                    return new RuntimeException(message);
                }
        );

        recipe.removeIngredientDescription(this.findById(id));
        recipeRepository.save(recipe);
        ingredientDescriptionRepository.deleteById(id);
    }
}
