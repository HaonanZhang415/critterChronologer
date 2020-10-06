package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PetService petService;

    public Long saveSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employeeList = new ArrayList<>();
        List<Pet> petList = new ArrayList<>();
        if (employeeIds != null) {
            for (Long employeeId : employeeIds) {
                employeeList.add(userService.findEmployeeById(employeeId));
            }
        }
        schedule.setEmployees(employeeList);

        if (petIds != null) {
            for (Long petId : petIds) {
                petList.add(petService.findPetById(petId));
            }
        }
        schedule.setPets(petList);

        return scheduleRepository.save(schedule).getId();
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleByPet(long petId) {
        Optional<Pet> petResponse = petRepository.findById(petId);
        Pet pet = petResponse.get();
        return scheduleRepository.findByPets_Id(pet.getId());
    }

    public List<Schedule> getScheduleByEmployee(long employeeId) {
        Optional<Employee> employeeResponse = employeeRepository.findById(employeeId);
        Employee employee = employeeResponse.get();
        return scheduleRepository.findByEmployees_Id(employee.getId());
    }

    public List<Schedule> getScheduleByCustomer(long customerId) {
        Optional<Customer> customerResponse = customerRepository.findById(customerId);
        Customer customer = customerResponse.get();
        List<Pet> pets = customer.getPets();
        List<Schedule> fullSchedules = new ArrayList<>();
        for (Pet pet : pets) {
            fullSchedules.addAll(scheduleRepository.findByPets_Id(pet.getId()));
        }
        return fullSchedules;
    }
}
