package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.DTO_in.GiftCardDTOin;
import org.example.labeebsystem.DTO_out.GiftCardDTOout;
import org.example.labeebsystem.Model.GiftCard;
import org.example.labeebsystem.Model.Student;
import org.example.labeebsystem.Repository.GiftCardRepository;
import org.example.labeebsystem.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftCardService {

    private final GiftCardRepository giftCardRepository;
    private final StudentRepository studentRepository;

    public List<GiftCardDTOout> getGiftCardsOfStudent(Integer studentId) {
        Student student = studentRepository.findStudentById(studentId);
        if (student == null)
            throw new ApiException("Student was not found");

        List<GiftCard> giftCards = giftCardRepository.findGiftCardByStudent(student);
        ArrayList<GiftCardDTOout> dtos = new ArrayList<>();
        for (GiftCard g: giftCards) {
            GiftCardDTOout dto = new GiftCardDTOout(g.getType(), g.getPrice(), g.getRedeemKey());
            dtos.add(dto);
        }
        return dtos;
    }

    public void buyGiftCard(Integer studentId, GiftCardDTOin giftCardDTO) {
        Student student = studentRepository.findStudentById(studentId);
        if (student == null)
            throw new ApiException("Student was not found");

        double price = Double.parseDouble(giftCardDTO.getPrice());

        if (student.getBalance() < price)
            throw new ApiException("Balance is not sufficient");

        GiftCard giftCard = new GiftCard(
                null,
                giftCardDTO.getType(),
                price,
                generateGiftCardKey(),
                student
        );

        student.setBalance(student.getBalance() - price);
        studentRepository.save(student);
        giftCardRepository.save(giftCard);
    }

    public String generateGiftCardKey() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            if (i > 0 && i % 4 == 0) {
                key.append("-");
            }
            key.append(characters.charAt(
                    random.nextInt(characters.length())));
        }
        return key.toString();
    }
}
