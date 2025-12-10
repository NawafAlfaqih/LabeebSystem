package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Category;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category findCategoryById(Integer id);
}
