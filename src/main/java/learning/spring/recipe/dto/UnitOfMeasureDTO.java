package learning.spring.recipe.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnitOfMeasureDTO {
    private Long id;
    @Column(unique=true)
    private String description;
}
