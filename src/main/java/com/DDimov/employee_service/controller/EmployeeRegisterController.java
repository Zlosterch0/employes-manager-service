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
public class AuthController {

  private final EmployeeFacade employeeFacade;

  @PostMapping("/register")
  public ResponseEntity<EmployeeDTO> register(
      @RequestBody Employee employee, @RequestParam List<Long> roleIds) {
    return ResponseEntity.ok(employeeFacade.registerEmployee(employee, roleIds));
  }

  @GetMapping("/login")
  public ResponseEntity<EmployeeDTO> login(@RequestParam String name) {
    return ResponseEntity.ok(employeeFacade.loginSimulation(name));
  }
}
