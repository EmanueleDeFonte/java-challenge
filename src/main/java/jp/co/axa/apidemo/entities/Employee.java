package jp.co.axa.apidemo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "EMPLOYEE")
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Setter
    @NotBlank(message = "Employee name cannot be empty.")
    @Column(name = "EMPLOYEE_NAME", nullable = false)
    private String name;

    @Setter
    @Min(value = 1, message = "Salary must be greater than 0")
    @Column(name = "EMPLOYEE_SALARY", nullable = false)
    private BigDecimal salary;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "DEPARTMENT", nullable = false)
    private DepartmentEnum department;

    @Setter
    @Size(min = 3, max = 50, message = "Username length must be between 3 and 50 characters.")
    @Column(name = "EMPLOYEE_USERNAME", nullable = false)
    private String employeeUsername;

    @Setter
    @Email
    @Column(name = "EMPLOYEE_EMAIL", nullable = false)
    private String email;

    @Setter
    @Size(min = 6, max = 72, message = "Password length must be between 6 and 72 characters.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$", message = "Password must contain at least one digit, one lowercase and one uppercase character.")
    @Column(name = "EMPLOYEE_PASSWORD", nullable = false)
    @JsonIgnore
    private String employeePassword;


}
