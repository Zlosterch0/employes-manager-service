package com.DDimov.employee_service.facade;

import com.DDimov.employee_service.dto.AddressDTO;
import com.DDimov.employee_service.dto.EmployeeDTO;
import com.DDimov.employee_service.entity.*;
import com.DDimov.employee_service.repository.AddressRepository;
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
  private final DepartmentService departmentService;
  private final ProjectService projectService;
  private final AddressRepository addressRepository;

  public List<EmployeeDTO> getHighPaid(Double salary, String requestorName) {
    Employee requestor = getAndValidateRequestor(requestorName);
    if (hasRole(requestor, "ROLE_ADMIN")) {
      return employeeService.findRich(salary).stream()
          .map(this::mapToDTO)
          .collect(Collectors.toList());
    }
    throw new RuntimeException("Access Denied: Insufficient permissions to filter salaries.");
  }

  @Transactional
  public EmployeeDTO registerEmployee(
      EmployeeDTO dto, List<Long> roleIds, List<Long> projectIds, String requestorName) {
    validateAdmin(requestorName);

    Employee employee = new Employee();
    employee.setName(dto.getName());
    employee.setSalary(dto.getSalary());

    if (dto.getAddress() != null) {
      Address addr =
          addressRepository
              .findByStreetAndStreetNumber(
                  dto.getAddress().getStreet(), dto.getAddress().getStreetNumber())
              .orElseGet(
                  () -> {
                    Address newAddr = new Address();
                    newAddr.setStreet(dto.getAddress().getStreet());
                    newAddr.setStreetNumber(dto.getAddress().getStreetNumber());
                    newAddr.setZipCode(dto.getAddress().getZipCode());
                    newAddr.setCountry(dto.getAddress().getCountry());
                    return addressRepository.save(newAddr);
                  });
      employee.setAddress(addr);
    }

    if (dto.getDepartmentName() != null) {
      Department dept = departmentService.findByName(dto.getDepartmentName());
      if (dept == null) {
        dept = new Department();
        dept.setName(dto.getDepartmentName());
        dept = departmentService.save(dept);
      }
      employee.setDepartment(dept);
    }

    employee.setRoles(
        roleIds.stream()
            .map(roleService::findById)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet()));
    if (projectIds != null) {
      employee.setProjects(
          projectIds.stream()
              .map(projectService::findById)
              .filter(Objects::nonNull)
              .collect(Collectors.toSet()));
    }

    return mapToDTO(employeeService.save(employee));
  }

  public List<EmployeeDTO> getAllEmployees(String requestorName) {
    Employee requestor = getAndValidateRequestor(requestorName);
    if (hasRole(requestor, "ROLE_ADMIN")) {
      return employeeService.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    return List.of(mapToDTO(requestor));
  }

  public EmployeeDTO loginSimulation(String name) {
    Employee emp = employeeService.findByName(name);
    if (emp == null) throw new RuntimeException("User not found");
    return mapToDTO(emp);
  }

  @Transactional
  public void deleteEmployee(Long id, String requestorName) {
    validateAdmin(requestorName);
    employeeService.delete(id);
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
      AddressDTO addrDto = new AddressDTO();
      addrDto.setStreet(emp.getAddress().getStreet());
      addrDto.setStreetNumber(emp.getAddress().getStreetNumber());
      addrDto.setZipCode(emp.getAddress().getZipCode());
      addrDto.setCountry(emp.getAddress().getCountry());
      dto.setAddress(addrDto);
    }

    if (emp.getProjects() != null) {
      dto.setProjectTitles(
          emp.getProjects().stream().map(Project::getTitle).collect(Collectors.toList()));
    }
    return dto;
  }

  private void validateAdmin(String name) {
    Employee requestor = getAndValidateRequestor(name);
    if (!hasRole(requestor, "ROLE_ADMIN")) {
      throw new RuntimeException("Forbidden: Admin access required.");
    }
  }

  private Employee getAndValidateRequestor(String name) {
    Employee emp = employeeService.findByName(name);
    if (emp == null) throw new RuntimeException("User not found.");
    return emp;
  }

  private boolean hasRole(Employee emp, String roleName) {
    return emp.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase(roleName));
  }
}
