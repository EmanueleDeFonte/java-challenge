package jp.co.axa.apidemo.controllers;

import javassist.NotFoundException;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public Page<Employee> getEmployees(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {

        try {

            Pageable pageable = PageRequest.of(page, size);
            return employeeService.retrieveEmployees(pageable);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

        }


    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name = "employeeId") Long employeeId) {

        try {

            return employeeService.getEmployee(employeeId);

        } catch (Exception e) {

            if (e instanceof NotFoundException) {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

            } else {

                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

            }

        }

    }

    @PostMapping("/employees")
    public Employee saveEmployee(@Valid @RequestBody Employee employee) {

        try {

            return employeeService.saveEmployee(employee);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

        }


    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {

        try {

            employeeService.deleteEmployee(employeeId);

        } catch (Exception e) {

            if (e instanceof NotFoundException) {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

            } else {

                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

            }

        }

    }

    @PutMapping("/employees/{employeeId}")
    public Employee updateEmployee(@Valid @RequestBody Employee employee,
                                   @PathVariable(name = "employeeId") Long employeeId) {

        try {

            return employeeService.updateEmployee(employee);

        } catch (Exception e) {

            if (e instanceof NotFoundException) {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

            } else {

                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

            }

        }

    }

}
