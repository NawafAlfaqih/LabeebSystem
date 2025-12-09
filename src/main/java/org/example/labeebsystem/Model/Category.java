package org.example.labeebsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name must be filled")
    @Pattern(regexp = "^[A-Za-z]+(?: [A-Za-z]+)*$",message = "name must be capital or small litters")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Teacher> teachers;
}