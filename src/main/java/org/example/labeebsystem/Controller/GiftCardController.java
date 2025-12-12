package org.example.labeebsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.DTO_in.GiftCardDTOin;
import org.example.labeebsystem.Service.GiftCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/giftcard")
public class GiftCardController {

    private final GiftCardService giftCardService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getGiftCardsOfStudent(@PathVariable Integer studentId) {
        return ResponseEntity.status(200).body(giftCardService.getGiftCardsOfStudent(studentId));
    }

    @PostMapping("/buy/{studentId}")
    public ResponseEntity<?> buyGiftCard(@PathVariable Integer studentId, @Valid @RequestBody GiftCardDTOin giftCardDTO) {
        giftCardService.buyGiftCard(studentId, giftCardDTO);
        return ResponseEntity.status(200).body("Gift card purchased successfully");
    }
}

