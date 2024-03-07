package learning.spring.recipe.repositories;

import learning.spring.recipe.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
