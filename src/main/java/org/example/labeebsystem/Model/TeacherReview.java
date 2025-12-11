package org.example.labeebsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TeacherReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Rating cannot be null")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot be more than 5")
    private Integer rating;

    @NotEmpty(message = "Comment cannot be empty")
    @Size(min = 3, max = 200, message = "Comment must be between 3 and 200 characters")
    private String comment;


    private LocalDateTime createdAt;

    @ManyToOne
    @JsonIgnore
    private Parent parent;


    @ManyToOne
    @JsonIgnore
    private Teacher teacher;
}