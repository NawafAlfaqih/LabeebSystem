package org.example.labeebsystem.DTO_in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GiftCardDTOin {

    @NotBlank(message = "Gift card type cannot be empty")
    @Pattern(
            regexp = "^(PS Store|XBOX|Apple|Google Play|Steam|Amazon)$",
            message = "Type must be a valid company (PS Store, XBOX, Apple, Google Play, Steam, Steam, Amazon)"
    )
    private String type;

    @NotNull(message = "Price cannot be null")
    @Pattern(
            regexp = "^(5|10|20|50|100)$",
            message = "Price must be one of the following values: 5, 10, 20, 50, 100"
    )
    private String price;
}
