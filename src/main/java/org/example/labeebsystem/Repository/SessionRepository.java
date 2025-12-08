package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session,Integer> {
    Session findSessionById(Integer id);
}
