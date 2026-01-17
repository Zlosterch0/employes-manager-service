package com.DDimov.employee_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Double salary;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "employee_roles",
      joinColumns = @JoinColumn(name = "employee_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  @ManyToOne
  @JoinColumn(name = "department_id")
  private Department department;

  @ManyToMany
  @JoinTable(
      name = "employee_projects",
      joinColumns = @JoinColumn(name = "employee_id"),
      inverseJoinColumns = @JoinColumn(name = "project_id"))
  private Set<Project> projects;

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;
}
