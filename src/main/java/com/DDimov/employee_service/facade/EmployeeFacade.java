package com.DDimov.employee_service.facade;

import com.DDimov.employee_service.dto.AddressDTO;
import com.DDimov.employee_service.dto.EmployeeDTO;
import com.DDimov.employee_service.entity.Address;
import com.DDimov.employee_service.entity.Employee;
import com.DDimov.employee_service.entity.Role;
import com.DDimov.employee_service.service.db.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class EmployeeFacade {

  private final EmployeeService employeeService;
  private final RoleService roleService;

  public List<EmployeeDTO> getAllEmployees(String requestorName) {
    Employee requestor = getAndValidateRequestor(requestorName);
    if (hasRole(requestor, "ROLE_ADMIN")) {
      return employeeService.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    if (hasRole(requestor, "ROLE_VIEW_SALARY")) {
      return List.of(mapToDTO(requestor));
    }

    throw new RuntimeException("Access Denied: You do not have permission to view employee lists.");
  }

  public EmployeeDTO registerEmployee(EmployeeDTO dto, List<Long> roleIds, String requestorName) {
    validateAdmin(requestorName);
    Employee employee = new Employee();
    employee.setName(dto.getName());
    employee.setSalary(dto.getSalary());

    Set<Role> roles =
        roleIds.stream()
            .map(roleService::findById)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    employee.setRoles(roles);

    if (dto.getAddress() != null) {
      Address addr = new Address();
      addr.setStreet(dto.getAddress().getStreet());
      addr.setStreetNumber(dto.getAddress().getStreetNumber());
      addr.setZipCode(dto.getAddress().getZipCode());
      addr.setCountry(dto.getAddress().getCountry());
      employee.setAddress(addr);
    }

    Employee savedEmployee = employeeService.save(employee);
    return mapToDTO(savedEmployee);
  }

  public EmployeeDTO loginSimulation(String name) {
    Employee emp = employeeService.findByName(name);
    if (emp == null) throw new RuntimeException("User not found");
    return mapToDTO(emp);
  }

  private EmployeeDTO mapToDTO(Employee emp) {
    EmployeeDTO dto = new EmployeeDTO();
    dto.setId(emp.getId());
    dto.setName(emp.getName());
    dto.setSalary(emp.getSalary());

    if (emp.getRoles() != null) {
      dto.setRolePermissions(
          emp.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
    }

    dto.setDepartmentName(emp.getDepartment() != null ? emp.getDepartment().getName() : "N/A");

    if (emp.getAddress() != null) {
      var a = emp.getAddress();
      AddressDTO addrDto = new AddressDTO();
      addrDto.setStreet(a.getStreet());
      addrDto.setStreetNumber(a.getStreetNumber());
      addrDto.setZipCode(a.getZipCode());
      addrDto.setCountry(a.getCountry());

      dto.setAddress(addrDto);
    }

    return dto;
  }

  public List<EmployeeDTO> getHighPaid(Double salary, String requestorName) {
    Employee requestor = getAndValidateRequestor(requestorName);

    if (hasRole(requestor, "ADMIN")) {
      return employeeService.findRich(salary).stream()
          .map(this::mapToDTO)
          .collect(Collectors.toList());
    }

    throw new RuntimeException("Access Denied: Insufficient permissions to filter salaries.");
  }

  @Transactional
  public EmployeeDTO createEmployee(Employee employee, String requestorName) {
    validateAdmin(requestorName);
    return mapToDTO(employeeService.save(employee));
  }

  @Transactional
  public void deleteEmployee(Long id, String requestorName) {
    validateAdmin(requestorName);
    employeeService.delete(id);
  }

  private void validateAdmin(String name) {
    Employee requestor = getAndValidateRequestor(name);
    if (!hasRole(requestor, "ROLE_ADMIN")) {
      throw new RuntimeException("Forbidden: You must be an ADMIN to perform this action.");
    }
  }

  private Employee getAndValidateRequestor(String name) {
    Employee emp = employeeService.findByName(name);
    if (emp == null) throw new RuntimeException("Authentication Error: Requestor not found.");
    return emp;
  }

  private boolean hasRole(Employee emp, String roleName) {
    return emp.getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase(roleName));
  }
}
