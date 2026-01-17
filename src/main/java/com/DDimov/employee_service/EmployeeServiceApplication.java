package com.DDimov.employee_service;

import com.DDimov.employee_service.entity.*;
import com.DDimov.employee_service.repository.DepartmentRepository;
import com.DDimov.employee_service.repository.EmployeeRepository;
import com.DDimov.employee_service.repository.ProjectRepository;
import com.DDimov.employee_service.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeeServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(EmployeeServiceApplication.class, args);
  }

  @Bean
  CommandLineRunner initData(
      RoleRepository roleRepo,
      DepartmentRepository deptRepo,
      EmployeeRepository empRepo,
      ProjectRepository projectRepo) {
    return args -> {
      Role adminRole =
          roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> roleRepo.save(new Role("ROLE_ADMIN")));
      Role viewSalary =
          roleRepo
              .findByName("ROLE_VIEW_SALARY")
              .orElseGet(() -> roleRepo.save(new Role("ROLE_VIEW_SALARY")));

      Department hr =
          deptRepo
              .findByName("Human Resources")
              .orElseGet(
                  () -> {
                    Department d = new Department();
                    d.setName("Human Resources");
                    return deptRepo.save(d);
                  });

      Project aiProject =
          projectRepo.findAll().stream()
              .filter(p -> p.getTitle().equals("AI Research"))
              .findFirst()
              .orElseGet(
                  () -> {
                    Project p = new Project();
                    p.setTitle("AI Research");
                    return projectRepo.save(p);
                  });

      if (empRepo.findByName("SuperAdmin").isEmpty()) {
        Employee adminUser = new Employee();
        adminUser.setName("SuperAdmin");
        adminUser.setSalary(10000.0);
        adminUser.setDepartment(hr);
        adminUser.setRoles(new HashSet<>(List.of(adminRole, viewSalary)));
        adminUser.setProjects(new HashSet<>(List.of(aiProject)));

        Address addr = new Address();
        addr.setStreet("Main St");
        addr.setStreetNumber("1");
        addr.setZipCode("1000");
        addr.setCountry("Bulgaria");
        adminUser.setAddress(addr);

        empRepo.save(adminUser);
        System.out.println(">>> SuperAdmin and Initial Data created.");
      }
    };
  }
}
