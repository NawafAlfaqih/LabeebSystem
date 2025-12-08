package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

    Task findTaskById(Integer id);
}
