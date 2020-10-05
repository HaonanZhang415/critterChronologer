package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends UserBaseRepository<Employee>{
    List<Employee> findByEmployeeSkillsInAndWorkDaysIn(Set<EmployeeSkill> skillsRequired, Set<DayOfWeek> dayOfWeek);
}
