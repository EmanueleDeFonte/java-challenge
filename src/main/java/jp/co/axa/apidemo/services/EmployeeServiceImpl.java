package jp.co.axa.apidemo.services;

import javassist.NotFoundException;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing Employee entities.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;

    }

    /**
     * Retrieves a page of employees.
     *
     * @param pageable the pagination information
     * @return a Page containing the employees
     */
    @Cacheable(value = "employees")
    public Page<Employee> retrieveEmployees(Pageable pageable) {

        return employeeRepository.findAll(pageable);

    }

    /**
     * Retrieves an employee by employeeId.
     *
     * @param employeeId the ID of the employee to retrieve
     * @return the Employee object with the specified employeeId
     * @throws Exception if there is an error while retrieving the employee
     */
    @Cacheable(value = "employees", key = "#employeeId")
    public Employee getEmployee(Long employeeId) throws Exception {

        return employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException("Employee not found."));

    }

    /**
     * Saves an employee.
     *
     * @param employee the Employee object to be saved
     * @return the saved Employee object
     */
    @CacheEvict(value = "employees", allEntries = true)
    public Employee saveEmployee(Employee employee) {

        // Encode the password of the employee and save the entity with the password encrypted.
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        employee.setEmployeePassword(passwordEncoder.encode(employee.getEmployeePassword()));
        return employeeRepository.save(employee);

    }

    /**
     * Deletes an employee by employeeId.
     *
     * @param employeeId the ID of the employee to delete
     * @throws Exception if there is an error while deleting the employee
     */
    @CacheEvict(value = "employees", allEntries = true)
    public void deleteEmployee(Long employeeId) throws Exception {

        employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException("Employee not found."));
        employeeRepository.deleteById(employeeId);

    }

    /**
     * Updates an employee.
     *
     * @param employee the updated Employee object
     * @return the updated Employee object
     * @throws Exception if there is an error while updating the employee
     */
    @CacheEvict(value = "employees", allEntries = true)
    public Employee updateEmployee(Employee employee) throws Exception {

        Employee oldEmployee = employeeRepository.findById(employee.getId()).orElseThrow(() -> new NotFoundException("Employee not found."));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // If the password of the entity is not the same, encrypt it and update the password for the employee.
        if (!employee.getEmployeePassword().equals(oldEmployee.getEmployeePassword())) {

            employee.setEmployeePassword(passwordEncoder.encode(employee.getEmployeePassword()));

        }

        return employeeRepository.save(employee);


    }

}