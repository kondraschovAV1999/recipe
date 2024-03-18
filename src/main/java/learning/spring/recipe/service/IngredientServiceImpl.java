package learning.spring.recipe.service;

import jakarta.transaction.Transactional;
import learning.spring.recipe.dto.IngredientDescriptionDTO;
import learning.spring.recipe.mappers.IngredientDescriptionMapper;
import learning.spring.recipe.model.IngredientDescription;
import learning.spring.recipe.repositories.IngredientDescriptionRepository;
import learning.spring.recipe.repositories.IngredientRepository;
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

    @Override
    public IngredientDescriptionDTO findByRecipeIdAndIngredientId(long recipeId, long ingredientId) {

        return ingredientDescriptionMapper.toDto(
                ingredientDescriptionRepository.findByIdAndRecipeId(ingredientId, recipeId)
                        .orElseThrow(() -> {
                            log.error("Ingredient Not Found by ingredientId:%d and recipeId:%d".formatted(ingredientId, recipeId));
                            return new RuntimeException("Ingredient Not Found by ingredient and recipe ids");
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


        IngredientDescription ingredientDescription =
                ingredientDescriptionRepository.findById(dto.getId())
                        .orElseGet(IngredientDescription::new);
        IngredientDescription ingredientDescriptionFromDto = ingredientDescriptionMapper.fromDto(dto);


        ingredientDescription.setIngredient(
                ingredientRepository.findByDescription(dto.getIngredient().getDescription().trim())
                        .orElseGet(() -> ingredientRepository.save(
                                ingredientDescriptionFromDto.getIngredient())
                        ));

        ingredientDescription.setUnitOfMeasure(
                unitOfMeasureRepository.findById(dto.getUom().getId())
                        .orElseThrow(() -> {
                            log.error("UOM with desc:%s and id:%d NOT FOUND".formatted(dto.getUom().getDescription(), dto.getUom().getId()));
                            return new RuntimeException("UOM NOT FOUND");
                        }));
        ingredientDescription.setAmount(ingredientDescriptionFromDto.getAmount());
        ingredientDescription.setRecipe(ingredientDescriptionFromDto.getRecipe());

        return ingredientDescriptionMapper.toDto(
                ingredientDescriptionRepository.save(ingredientDescription));
    }
}
