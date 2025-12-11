package org.example.labeebsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class StudentPayment  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Pattern(regexp = "^(Full|Installments)$")
    @Column(columnDefinition = "varchar(12) not null")
    private String paymentType;

    @Column(columnDefinition = "varchar(10)")
    private String discountCode;

    @NotNull(message = "final price cannot be empty")
    private Double finalPrice;

    private Integer totalInstallments;

    private Integer remainingInstallments;

    private Double InstallmentAmount;

    @OneToOne
    @MapsId
    @JsonIgnore
    private CourseSchedule courseSchedule;
}