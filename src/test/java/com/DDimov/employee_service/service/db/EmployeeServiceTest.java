package com.DDimov.employee_service.service.db;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.DDimov.employee_service.entity.Employee;
import com.DDimov.employee_service.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

  @Mock private EmployeeRepository repository;

  @InjectMocks private EmployeeService employeeService;

  @Test
  void givenSalaryThreshold_whenFindRich_thenReturnsHighEarners() {
    // Given
    Double threshold = 5000.0;
    Employee emp = new Employee();
    emp.setSalary(6000.0);
    given(repository.findHighEarners(threshold)).willReturn(List.of(emp));

    // When
    List<Employee> result = employeeService.findRich(threshold);

    // Then
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(6000.0, result.get(0).getSalary());
    verify(repository).findHighEarners(threshold);
  }

  @Test
  void givenName_whenFindByName_thenReturnsEmployee() {
    // Given
    String name = "SuperAdmin";
    Employee emp = new Employee();
    emp.setName(name);
    given(repository.findByName(name)).willReturn(Optional.of(emp));

    // When
    Employee result = employeeService.findByName(name);

    // Then
    assertNotNull(result);
    assertEquals(name, result.getName());
    verify(repository).findByName(name);
  }

  @Test
  void givenEmployee_whenSave_thenReturnsSavedEmployee() {
    // Given
    Employee emp = new Employee();
    given(repository.save(emp)).willReturn(emp);

    // When
    Employee saved = employeeService.save(emp);

    // Then
    assertEquals(emp, saved);
    verify(repository).save(emp);
  }
}
