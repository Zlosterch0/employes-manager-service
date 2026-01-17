package com.DDimov.employee_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeDTO {
  private Long id;
  private String name;
  private Double salary;
  private List<String> rolePermissions;
  private String departmentName;
  private AddressDTO address;
  private List<String> projectTitles;
}
