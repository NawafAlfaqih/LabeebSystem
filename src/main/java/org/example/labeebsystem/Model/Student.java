package org.example.labeebsystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @PositiveOrZero(message = "Balance must be zero or positive")
    private Integer balance = 0;

    @Min(value = 0, message = "Total grade must be 0 or more")
    @Max(value = 100, message = "Total grade cannot exceed 100")
    private Integer totalGrade = 0;
}
