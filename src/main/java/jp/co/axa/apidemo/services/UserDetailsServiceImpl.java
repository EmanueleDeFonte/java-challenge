package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Collections.emptyList;

/**
 * Service implementation for loading user details based on the username.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    public UserDetailsServiceImpl(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;

    }

    /**
     * Loads user details by username.
     *
     * @param username the username of the user to load
     * @return a UserDetails object representing the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Check if the user exists.
        // If it is the case return a UserDetails object which encapsulate the Employee username, password and authorities.
        Employee employee = employeeRepository.findByEmployeeUsername(username)
                .orElseThrow(() -> {

                    logger.error("User not found with username: " + username);
                    return new UsernameNotFoundException("User not found with username: " + username);

                });


        return new User(employee.getEmployeeUsername(), employee.getEmployeePassword(), emptyList());

    }

}

