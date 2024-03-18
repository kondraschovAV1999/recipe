package learning.spring.recipe.service;

import learning.spring.recipe.dto.UnitOfMeasureDTO;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureDTO> listAllUoms();
}
