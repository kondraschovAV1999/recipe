package learning.spring.recipe.service;

import learning.spring.recipe.dto.UnitOfMeasureDTO;
import learning.spring.recipe.mappers.UnitOfMeasureMapper;
import learning.spring.recipe.repositories.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository repository;
    private final UnitOfMeasureMapper mapper;

    @Override
    public Set<UnitOfMeasureDTO> listAllUoms() {

        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::toDto)
                .collect(Collectors.toSet());
    }
}
