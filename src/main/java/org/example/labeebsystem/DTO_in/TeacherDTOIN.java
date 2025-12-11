package org.example.labeebsystem.DTO_in;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherDTOIN {

    @NotEmpty(message = "name can't be empty")
    @Size(min = 2,max = 15,message = "name must be 2 litters minimum, 15 litters maximum")
    private String name;

    @NotBlank(message = "email cannot be blank.")
    @Email
    private String email;

    @NotBlank(message = "password cannot be blank.")
    @Size(min = 8, message = "password length must be at least '8'.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.,?!])(?=\\S+$).{8,20}$",
            message = "Password must contain at least one digit, one lowercase letter, " +
                    "one uppercase letter, one special character, and be 8-20 characters long.")
    private String password;

    @NotBlank(message = "major cannot be empty")
    private String major;

    @NotBlank(message = "bio cannot be empty")
    private String bio;

    @NotBlank(message = "categoryType cannot be empty")
    private String categoryType;

}
