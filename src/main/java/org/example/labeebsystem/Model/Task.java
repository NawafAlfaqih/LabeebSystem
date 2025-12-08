package org.example.labeebsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int default 0")
    @PositiveOrZero(message = "grade must be positive or zero")
    @Max(value = 100)
    private Integer grade;

    @NotBlank(message = "bio cannot be empty")
    @Column(columnDefinition = "varchar(256) not null")
    private String description;

    @NotNull(message = "dueDate cannot be null.")
    @FutureOrPresent(message = "dueDate cannot be in the past.")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime dueDate;

    @Column(columnDefinition = "TEXT")
    private String fileAnswerUrl;

    @Pattern(regexp = "^(Pending|Uploaded|Approved|Rejected)$")
    @Column(columnDefinition = "varchar(7) not null")
    private String status; //Pending - Uploaded - Approved - Rejected

}
