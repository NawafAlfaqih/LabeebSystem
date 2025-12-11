package org.example.labeebsystem.DTO_in;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcuseDTO {

    @NotEmpty(message = "excuseText can not be empty")
    private String excuseText;


    private String fileUrl;
}
