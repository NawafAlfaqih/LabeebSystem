package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.GiftCard;
import org.example.labeebsystem.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftCardRepository extends JpaRepository<GiftCard, Integer> {

    GiftCard findGiftCardById(Integer id);

    List<GiftCard> findGiftCardByStudent(Student student);
}
