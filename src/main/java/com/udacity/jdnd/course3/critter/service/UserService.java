package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PetRepository petRepository;

    public Long saveCustomer(Customer customer) {
        return customerRepository.save(customer).getId();
    }

    public Long saveEmployee(Employee employee) {
        return employeeRepository.save(employee).getId();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Employee findEmployeeById(long employeeId) {
        Optional<Employee> employeeResponse = employeeRepository.findById(employeeId);
        Employee employee = employeeResponse.get();
        return employee;
    }

    public Customer getOwnerByPetId(long petId) {
        Optional<Pet> petResponse = petRepository.findById(petId);
        Pet pet = petResponse.get();
        return pet.getCustomer();
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = findEmployeeById(employeeId);
        employee.setWorkDays(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeeForService(Set<EmployeeSkill> skillsRequired, LocalDate dateRequired) {

        DayOfWeek dayOfWeek = dateRequired.getDayOfWeek();
        Set<DayOfWeek> dayOfWeeks = new HashSet<>();
        dayOfWeeks.add(dayOfWeek);
        return employeeRepository.findByEmployeeSkillsInAndWorkDaysIn(skillsRequired, dayOfWeeks);
    }
}
