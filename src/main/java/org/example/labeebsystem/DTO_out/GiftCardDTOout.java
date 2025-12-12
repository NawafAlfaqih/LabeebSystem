package org.example.labeebsystem.DTO_out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GiftCardDTOout {

    private String type;

    private Double price;

    private String key;
}
