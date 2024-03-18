package learning.spring.recipe.service;

import learning.spring.recipe.dto.UnitOfMeasureDTO;
import learning.spring.recipe.mappers.UnitOfMeasureMapper;
import learning.spring.recipe.model.UnitOfMeasure;
import learning.spring.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {

    private UnitOfMeasureMapper mapper = UnitOfMeasureMapper.INSTANCE;
    private UnitOfMeasureService service;
    @Mock
    private UnitOfMeasureRepository repository;

    @BeforeEach
    void setUp() {
        service = new UnitOfMeasureServiceImpl(repository, mapper);
    }

    @Test
    void listAllUoms() {
        //given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        uom1.setDescription("uom1");
        unitOfMeasures.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        uom2.setDescription("uom2");
        unitOfMeasures.add(uom2);

        when(repository.findAll()).thenReturn(unitOfMeasures);

        //when
        Set<UnitOfMeasureDTO> dtos = service.listAllUoms();

        //then
        assertEquals(2, dtos.size());
        verify(repository).findAll();
    }
}