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

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;

    }

    @Cacheable(value = "employees")
    public Page<Employee> retrieveEmployees(Pageable pageable) {

        return employeeRepository.findAll(pageable);

    }

    @Cacheable(value = "employees", key = "#employeeId")
    public Employee getEmployee(Long employeeId) throws Exception {

        return employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException("Employee not found."));

    }

    @CacheEvict(value = "employees", allEntries = true)
    public Employee saveEmployee(Employee employee) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        employee.setEmployeePassword(passwordEncoder.encode(employee.getEmployeePassword()));
        return employeeRepository.save(employee);

    }

    @CacheEvict(value = "employees", allEntries = true)
    public void deleteEmployee(Long employeeId) throws Exception {

        employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException("Employee not found."));
        employeeRepository.deleteById(employeeId);

    }

    @CacheEvict(value = "employees", allEntries = true)
    public Employee updateEmployee(Employee employee) throws Exception {
        Employee oldEmployee = employeeRepository.findById(employee.getId()).orElseThrow(() -> new NotFoundException("Employee not found."));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!employee.getEmployeePassword().equals(oldEmployee.getEmployeePassword())) {

            employee.setEmployeePassword(passwordEncoder.encode(employee.getEmployeePassword()));

        }

        return employeeRepository.save(employee);


    }

}