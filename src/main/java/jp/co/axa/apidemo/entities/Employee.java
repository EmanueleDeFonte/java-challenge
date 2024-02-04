package jp.co.axa.apidemo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jp.co.axa.apidemo.utils.CustomAuthorityDeserializer;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents an employee entity.
 * This class maps the employee entity to the "EMPLOYEE" table in the database.
 * Implements the UserDetails interface for integration with Spring Security.
 */
@Entity
@Table(name = "EMPLOYEE")
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @NotNull
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
    private String employeePassword;

    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        return authorities;

    }

    @Override
    public String getPassword() {

        return employeePassword;

    }

    @Override
    public String getUsername() {

        return employeeUsername;

    }

    @Override
    public boolean isAccountNonExpired() {

        // TODO: Expiring account behavior not managed
        return true;

    }

    @Override
    public boolean isAccountNonLocked() {

        // TODO: Locking account behavior not managed
        return true;

    }

    @Override
    public boolean isCredentialsNonExpired() {

        // TODO: Credential account behavior not managed
        return true;

    }

    @Override
    public boolean isEnabled() {

        // TODO: Account enable/disable behavior not managed
        return true;

    }

}
