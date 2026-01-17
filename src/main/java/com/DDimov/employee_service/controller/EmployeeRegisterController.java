package com.DDimov.employee_service.controller;

import com.DDimov.employee_service.dto.EmployeeDTO;
import com.DDimov.employee_service.entity.Employee;
import com.DDimov.employee_service.facade.EmployeeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class EmployeeRegisterController {

  private final EmployeeFacade employeeFacade;

  @PostMapping("/register")
  public ResponseEntity<?> register(
      @RequestBody EmployeeDTO employee,
      @RequestParam List<Long> roleIds,
      @RequestHeader("X-Logged-User") String requestorName) {
    return ResponseEntity.ok(employeeFacade.registerEmployee(employee, roleIds, requestorName));
  }

  @GetMapping("/login")
  public ResponseEntity<?> login(@RequestParam String name) {
    return ResponseEntity.ok(employeeFacade.loginSimulation(name));
  }
}
