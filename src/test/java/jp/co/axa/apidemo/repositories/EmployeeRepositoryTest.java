package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.DepartmentEnum;
import jp.co.axa.apidemo.entities.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class EmployeeRepositoryTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {

        Long employeeId = 1L;
        Employee employee = new Employee(1L, "Sophia Brown", new BigDecimal("5000.25") ,
                DepartmentEnum.DEPARTMENT_1, "SophiaB",
                "sophia.brown@email.com","SophiaPass123");
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeRepository.findById(employeeId);

        Assertions.assertEquals(Optional.of(employee), result);

    }

    @Test
    public void testFindByEmployeeUsername() {

        String employeeUsername = "Aaa Bbb";
        Employee employee = new Employee(1L, "Sophia Brown", new BigDecimal("5000.25") ,
                DepartmentEnum.DEPARTMENT_1, "SophiaB",
                "sophia.brown@email.com","SophiaPass123");
        Mockito.when(employeeRepository.findByEmployeeUsername(Mockito.anyString())).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeRepository.findByEmployeeUsername(employeeUsername);

        Assertions.assertEquals(Optional.of(employee), result);

    }

    @Test
    public void testCreate() {

        Employee employee = new Employee(1L, "Sophia Brown", new BigDecimal("5000.25") ,
                DepartmentEnum.DEPARTMENT_1, "SophiaB",
                "sophia.brown@email.com","SophiaPass123");
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        Employee result = employeeRepository.save(employee);

        Assertions.assertEquals(employee, result);

    }

    @Test
    public void testUpdate() {

        Employee existingEmployee = new Employee(1L, "Sophia Brown", new BigDecimal("5000.25") ,
                DepartmentEnum.DEPARTMENT_1, "SophiaB",
                "sophia.brown@email.com","SophiaPass123");
        Employee updatedEmployee = new Employee(1L, "James Smith", new BigDecimal("1200") ,
                DepartmentEnum.DEPARTMENT_2, "JamesS",
                "james.smith@email.com","JamesPass123");

        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingEmployee));
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(updatedEmployee);

        Employee result = employeeRepository.save(updatedEmployee);

        Assertions.assertEquals(updatedEmployee, result);

    }

    @Test
    public void testDelete() {

        Long employeeId = 1L;

        employeeRepository.deleteById(employeeId);

        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(employeeId);

    }
}


