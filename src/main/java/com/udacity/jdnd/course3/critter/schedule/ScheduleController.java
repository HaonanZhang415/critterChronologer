package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Long id = scheduleService.saveSchedule(convertScheduleDTOToScheduleEntity(scheduleDTO), scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds());
        scheduleDTO.setId(id);
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return convertScheduleEntityToScheduleDTOForList(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        List<Schedule> schedules = scheduleService.getScheduleByPet(petId);
        return convertScheduleEntityToScheduleDTOForList(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getScheduleByEmployee(employeeId);
        return convertScheduleEntityToScheduleDTOForList(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleByCustomer(customerId);
        return convertScheduleEntityToScheduleDTOForList(schedules);
    }

    private Schedule convertScheduleDTOToScheduleEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }

    private ScheduleDTO convertScheduleEntityToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        //scheduleDTO.setActivities(schedule.getActivities());
        if (schedule.getEmployees() != null) {
            List<Long> employeeIds = new ArrayList<>();
            for (Employee employee : schedule.getEmployees()) {
                employeeIds.add(employee.getId());
            }
            scheduleDTO.setEmployeeIds(employeeIds);
        }
        if (schedule.getPets() != null) {
            List<Long> petIds = new ArrayList<>();
            for (Pet pet : schedule.getPets()) {
                petIds.add(pet.getId());
            }
            scheduleDTO.setPetIds(petIds);
        }

        return scheduleDTO;
    }

    private List<ScheduleDTO> convertScheduleEntityToScheduleDTOForList(List<Schedule> schedules) {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOs.add(convertScheduleEntityToScheduleDTO(schedule));
        }
        return scheduleDTOs;
    }
}
