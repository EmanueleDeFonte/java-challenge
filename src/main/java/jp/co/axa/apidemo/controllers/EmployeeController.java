package jp.co.axa.apidemo.controllers;

import javassist.NotFoundException;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    public void setEmployeeService(EmployeeService employeeService) {

        this.employeeService = employeeService;

    }

    /**
     * Retrieves employees using pagination.
     *
     * @param page the page number to retrieve (default: 0)
     * @param size the number of employees per page (default: 10)
     * @return a Page of Employee objects
     * @throws ResponseStatusException if there is an error while retrieving the employees
     */
    @GetMapping("/employees")
    public Page<Employee> getEmployees(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {

        try {

            Pageable pageable = PageRequest.of(page, size);
            return employeeService.retrieveEmployees(pageable);

        } catch (Exception e) {

            logger.error("Error during /api/v1/employees GET execution: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

        }


    }

    /**
     * Retrieves an employee using employeeId.
     *
     * @param employeeId the ID of the employee to retrieve
     * @return the Employee object
     * @throws ResponseStatusException if the employee is not found or there is an error while retrieving the employee
     */
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name = "employeeId") Long employeeId) {

        try {

            return employeeService.getEmployee(employeeId);

        } catch (Exception e) {

            logger.error("Error during /api/v1/employees/{employeeId} GET execution: {}", e.getMessage());

            if (e instanceof NotFoundException) {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

            } else {

                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

            }

        }

    }

    /**
     * Retrieves an employee using employeeId.
     *
     * @param employeeUsername the username of the employee to retrieve
     * @return the Employee object
     * @throws ResponseStatusException if the employee is not found or there is an error while retrieving the employee
     */
    @GetMapping("/employees/username/{employeeUsername}")
    public Employee getEmployeeByUsername(@PathVariable(name = "employeeUsername") String employeeUsername) {

        try {

            return employeeService.getEmployeeByUsername(employeeUsername);

        } catch (Exception e) {

            logger.error("Error during /api/v1/employees/{employeeUsername} GET execution: {}", e.getMessage());

            if (e instanceof NotFoundException) {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

            } else {

                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

            }

        }

    }

    /**
     * Saves an employee.
     *
     * @param employee the Employee object to be saved
     * @return the saved Employee object
     * @throws ResponseStatusException if there is an error during saving process
     */
    @PostMapping("/employees")
    public Employee saveEmployee(@Valid @RequestBody Employee employee) {

        try {

            return employeeService.saveEmployee(employee);

        } catch (Exception e) {

            logger.error("Error during /api/v1/employees POST execution: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

        }


    }

    /**
     * Deletes an employee by employeeId.
     *
     * @param employeeId the Id of the employee to delete
     * @throws ResponseStatusException if the employee is not found or there is an error while deleting the employee
     */
    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {

        try {

            employeeService.deleteEmployee(employeeId);

        } catch (Exception e) {

            logger.error("Error during /api/v1/employees/{employeeId} DELETE execution: {}", e.getMessage());

            if (e instanceof NotFoundException) {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

            } else {

                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

            }

        }

    }

    /**
     * Updates an employee using employeeId.
     *
     * @param employee   the updated Employee object
     * @return the updated Employee object
     * @throws ResponseStatusException if the employee is not found or there is an error while updating the employee
     */
    @PutMapping("/employees/{employeeId}")
    public Employee updateEmployee(@Valid @RequestBody Employee employee) {

        try {

            return employeeService.updateEmployee(employee);

        } catch (Exception e) {

            logger.error("Error during /api/v1/employees/{employeeId} PUT execution: {}", e.getMessage());

            if (e instanceof NotFoundException) {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

            } else {

                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

            }

        }

    }

}