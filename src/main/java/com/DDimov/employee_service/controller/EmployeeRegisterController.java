package com.DDimov.employee_service.controller;

import com.DDimov.employee_service.dto.EmployeeDTO;
import com.DDimov.employee_service.facade.EmployeeFacade;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class EmployeeRegisterController {

  private final EmployeeFacade employeeFacade;

  @PostMapping("/register")
  public ResponseEntity<EmployeeDTO> register(
      @RequestBody EmployeeDTO employee,
      @RequestParam List<Long> roleIds,
      @RequestParam(required = false) List<Long> projectIds,
      @RequestHeader("X-Logged-User") String requestorName) {

    EmployeeDTO registeredEmployee =
        employeeFacade.registerEmployee(employee, roleIds, projectIds, requestorName);

    return ResponseEntity.ok(registeredEmployee);
  }

  @GetMapping("/login")
  public ResponseEntity<?> login(@RequestParam String name) {
    return ResponseEntity.ok(employeeFacade.loginSimulation(name));
  }
}
