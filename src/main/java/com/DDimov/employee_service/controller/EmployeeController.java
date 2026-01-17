package com.DDimov.employee_service.controller;@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/filter")
    public ResponseEntity<EmployeeDTO> getRich(@RequestParam Double salary) {
        return ResponseEntity.ok(employeeService.getHighEarner(salary));
    }
}