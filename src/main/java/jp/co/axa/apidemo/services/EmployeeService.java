package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing Employee entities.
 */
public interface EmployeeService {

    /**
     * Retrieves all the employees.
     *
     * @param pageable the pagination information
     * @return a Page of Employee
     */
    Page<Employee> retrieveEmployees(Pageable pageable);

    /**
     * Retrieves an employee using employeeId.
     *
     * @param employeeId the ID of the employee to retrieve
     * @return the Employee object
     * @throws Exception if there is an error while retrieving the employee
     */
    Employee getEmployee(Long employeeId) throws Exception;

    /**
     * Saves an employee.
     *
     * @param employee the Employee object to be saved
     * @return the saved Employee object
     */
    Employee saveEmployee(Employee employee);

    /**
     * Deletes an employee using employeeId.
     *
     * @param employeeId the ID of the employee to delete
     * @throws Exception if there is an error while deleting the employee
     */
    void deleteEmployee(Long employeeId) throws Exception;

    /**
     * Updates an employee.
     *
     * @param employee the updated Employee object
     * @return the updated Employee object
     * @throws Exception if there is an error while updating the employee
     */
    Employee updateEmployee(Employee employee) throws Exception;

}