package com.DDimov.employee_service.repository;

import com.DDimov.employee_service.entity.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  @Query("SELECT e FROM Employee e WHERE e.salary >= :minSalary ORDER BY e.name ASC")
  List<Employee> findHighEarners(@Param("minSalary") Double minSalary);

  Optional<Employee> findByName(String name);
}
