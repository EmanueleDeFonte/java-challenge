package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Employee entities in the database.
 * Extends JpaRepository to inherit basic CRUD.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    /**
     * Finds an employee using username.
     *
     * @param username the username of the employee
     * @return an Optional containing the Employee object if found
     */
    Optional<Employee> findByEmployeeUsername(String username);

}
