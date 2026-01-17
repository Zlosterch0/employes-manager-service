package com.DDimov.employee_service.controller;

import com.DDimov.employee_service.entity.Employee;
import com.DDimov.employee_service.facade.EmployeeFacade;
import com.DDimov.employee_service.service.db.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeFacade employeeFacade;

  @GetMapping
  public ResponseEntity<?> getAll(@RequestHeader("X-Logged-User") String requestorName) {
    return ResponseEntity.ok(employeeFacade.getAllEmployees(requestorName));
  }

  @GetMapping("/filter")
  public ResponseEntity<?> getRich(
      @RequestParam Double salary, @RequestHeader("X-Logged-User") String requestorName) {
    return ResponseEntity.ok(employeeFacade.getHighPaid(salary, requestorName));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(
      @PathVariable Long id, @RequestHeader("X-Logged-User") String requestorName) {
    employeeFacade.deleteEmployee(id, requestorName);
    return ResponseEntity.ok().build();
  }
}
