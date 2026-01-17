package com.DDimov.employee_service.service.db;@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getHighEarner(Double minSalary) {
        // Using your @Query from the Repo
        return employeeRepository.findByHighSalary(minSalary).stream()
                .map(this::mapToDTO)
                .findFirst()
                .orElse(null);
    }

    // This is your "Mapper" logic
    private EmployeeDTO mapToDTO(Employee emp) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(emp.getId());
        dto.setName(emp.getName());
        dto.setSalary(emp.getSalary());
        dto.setDepartmentName(emp.getDepartment() != null ? emp.getDepartment().getName() : "None");
        return dto;
    }
}