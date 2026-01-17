package com.DDimov.employee_service.service.db;

import com.DDimov.employee_service.entity.Employee;
import com.DDimov.employee_service.repository.EmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
  private final EmployeeRepository repository;

  public List<Employee> findAll() {
    return repository.findAll();
  }

  public List<Employee> findRich(Double salary) {
    return repository.findHighEarners(salary);
  }

  public Employee findByName(String name) {
    return repository.findByName(name).orElse(null);
  }

  public Employee save(Employee emp) {
    return repository.save(emp);
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }
}
