package org.example.labeebsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "email cannot be blank.")
    @Column(columnDefinition = "varchar(40) not null unique")
    @Email
    private String email;
    
    @NotBlank(message = "password cannot be blank.")
    @Size(min = 8, message = "password length must be at least '8'.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.,?!])(?=\\S+$).{8,20}$",
            message = "Password must contain at least one digit, one lowercase letter, " +
                    "one uppercase letter, one special character, and be 8-20 characters long.")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;
}
