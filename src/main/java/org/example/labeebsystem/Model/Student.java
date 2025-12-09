package org.example.labeebsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    @NotNull(message = "Balance cannot be null")
    @PositiveOrZero(message = "Balance must be zero or positive")
    private Integer balance;

    @NotNull(message = "Total grade cannot be null")
    @Min(value = 0, message = "Total grade must be 0 or more")
    @Max(value = 100, message = "Total grade cannot be more 100")
    private Integer totalGrade;

    @ManyToOne
    @JsonIgnore
    private Parent parent;
}
