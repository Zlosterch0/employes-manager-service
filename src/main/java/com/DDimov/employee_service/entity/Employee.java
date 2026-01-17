package com.DDimov.employee_service.entity;@Entity @Data
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double salary;

    @ManyToOne // Requirement: ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany // Requirement: ManyToMany
    @JoinTable(name = "employee_projects", 
               joinColumns = @JoinColumn(name = "employee_id"), 
               inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<Project> projects;

    @OneToOne(cascade = CascadeType.ALL) // Table 4
    private Address address;
}