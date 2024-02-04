package jp.co.axa.apidemo.services;


import javassist.NotFoundException;
import jp.co.axa.apidemo.entities.DepartmentEnum;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        employeeService = new EmployeeServiceImpl();
        employeeService.setEmployeeRepository(employeeRepository);

    }

    @Test
    void testRetrieveEmployees() {

        Pageable pageable = Mockito.mock(Pageable.class);
        Page<Employee> employees = Mockito.mock(Page.class);
        Mockito.when(employeeRepository.findAll(Mockito.any(Pageable.class))).thenReturn(employees);

        Page<Employee> result = employeeService.retrieveEmployees(pageable);

        Assertions.assertEquals(employees, result);

    }

    @Test
    void testGetEmployee() throws Exception {

        Long employeeId = 1L;
        Employee employee = new Employee(1L, "Sophia Brown", new BigDecimal("5000.25") ,
                DepartmentEnum.DEPARTMENT_1, "SophiaB",
                "sophia.brown@email.com","SophiaPass123");
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployee(employeeId);

        Assertions.assertEquals(employee, result);

    }

    @Test
    void testGetEmployee_NotFound() {

        Long employeeId = 1L;
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> {

            employeeService.getEmployee(employeeId);

        });

    }

    @Test
    void testSaveEmployee() {

        Employee employee = new Employee(1L, "Sophia Brown", new BigDecimal("5000.25") ,
                DepartmentEnum.DEPARTMENT_1, "SophiaB",
                "sophia.brown@email.com","SophiaPass123");

        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        Employee result = employeeService.saveEmployee(employee);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isPasswordMatching = passwordEncoder.matches("SophiaPass123", result.getEmployeePassword());

        Assertions.assertEquals(true, isPasswordMatching);

    }

    @Test
    void testDeleteEmployee() throws Exception {

        Long employeeId = 1L;
        Employee employee = new Employee(1L, "Sophia Brown", new BigDecimal("5000.25") ,
                DepartmentEnum.DEPARTMENT_1, "SophiaB",
                "sophia.brown@email.com","SophiaPass123");
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(employeeId);

        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(employeeId);

    }

    @Test
    void testUpdateEmployee() throws Exception {

        Employee employee = new Employee(1L, "Sophia Brown", new BigDecimal("5000.25") ,
                DepartmentEnum.DEPARTMENT_1, "SophiaB",
                "sophia.brown@email.com","SophiaPass123");
        Employee oldEmployee = new Employee(1L, "Sophia Brown", new BigDecimal("1250") ,
                DepartmentEnum.DEPARTMENT_1, "SophiaB",
                "sophia.brown@email.com","$2a$12$nLEFSdraGRRUfysBZS.F9.rl.sZlzJnlbaTn3rb0eTYdCZrgLiYJW");
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(oldEmployee));
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        Employee result = employeeService.updateEmployee(employee);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isPasswordMatching = passwordEncoder.matches("SophiaPass123", result.getEmployeePassword());

        Assertions.assertEquals(true, isPasswordMatching);

    }

}

