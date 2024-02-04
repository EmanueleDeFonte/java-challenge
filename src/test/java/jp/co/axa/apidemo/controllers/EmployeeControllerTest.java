package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.DepartmentEnum;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        employeeController = new EmployeeController();
        employeeController.setEmployeeService(employeeService);

    }

    @Test
    void testGetEmployees() {

        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employees = Mockito.mock(Page.class);
        when(employeeService.retrieveEmployees(Mockito.any(Pageable.class))).thenReturn(employees);

        Page<Employee> result = employeeController.getEmployees(page, size);

        Assertions.assertEquals(employees, result);

    }

    @Test
    void testGetEmployee() throws Exception {

        Long employeeId = 1L;
        Employee employee = new Employee(1L, "Sophia Brown", new BigDecimal("5000.25") ,
                DepartmentEnum.DEPARTMENT_1, "SophiaB",
                "sophia.brown@email.com","SophiaPass123");
        when(employeeService.getEmployee(Mockito.anyLong())).thenReturn(employee);

        Employee result = employeeController.getEmployee(employeeId);

        Assertions.assertEquals(employee, result);

    }

    @Test
    void testSaveEmployee() {

        Employee employee = new Employee(1L, "Sophia Brown", new BigDecimal("5000.25") ,
                DepartmentEnum.DEPARTMENT_1, "SophiaB",
                "sophia.brown@email.com","SophiaPass123");
        when(employeeService.saveEmployee(Mockito.any(Employee.class))).thenReturn(employee);

        Employee result = employeeController.saveEmployee(employee);

        Assertions.assertEquals(employee, result);

    }

    @Test
    void testDeleteEmployee() throws Exception {

        Long employeeId = 1L;

        Assertions.assertDoesNotThrow(() -> {

            employeeController.deleteEmployee(employeeId);

        });

        verify(employeeService, Mockito.times(1)).deleteEmployee(employeeId);

    }

    @Test
    void testUpdateEmployee() throws Exception {

        Long employeeId = 1L;
        Employee employee = new Employee(1L, "Sophia Brown", new BigDecimal("5000.25") ,
                DepartmentEnum.DEPARTMENT_1, "SophiaB",
                "sophia.brown@email.com","SophiaPass123");
        Mockito.when(employeeService.updateEmployee(Mockito.any(Employee.class))).thenReturn(employee);

        Employee result = employeeController.updateEmployee(employee, employeeId);

        Assertions.assertEquals(employee, result);

    }

}

