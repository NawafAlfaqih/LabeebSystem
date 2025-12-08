package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent,Integer> {

    Parent findParentById(Integer id);
}
