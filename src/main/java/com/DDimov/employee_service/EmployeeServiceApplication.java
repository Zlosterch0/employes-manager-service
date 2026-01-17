package com.DDimov.employee_service;

import com.DDimov.employee_service.entity.Address;
import com.DDimov.employee_service.entity.Department;
import com.DDimov.employee_service.entity.Employee;
import com.DDimov.employee_service.entity.Role;
import com.DDimov.employee_service.repository.DepartmentRepository;
import com.DDimov.employee_service.repository.EmployeeRepository;
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
      RoleRepository roleRepo, DepartmentRepository deptRepo, EmployeeRepository empRepo) {
    return args -> {
      Role adminRole =
          roleRepo
              .findByName("ROLE_ADMIN")
              .orElseGet(() -> roleRepo.save(new Role( "ROLE_ADMIN")));


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

      if (empRepo.findByName("SuperAdmin").isEmpty()) {
        Employee adminUser = new Employee();
        adminUser.setName("SuperAdmin");
        adminUser.setSalary(10000.0);
        adminUser.setDepartment(hr);
        adminUser.setRoles(new HashSet<>(List.of(adminRole, addEmp, viewSalary)));

        Address addr = new Address();
        addr.setStreet("Main St");
        addr.setStreetNumber("1");
        addr.setZipCode("1000");
        addr.setCountry("Bulgaria");
        adminUser.setAddress(addr);

        empRepo.save(adminUser);
        System.out.println(">>> SuperAdmin created.");
      }

      if (empRepo.findByName("SimpleUser").isEmpty()) {
        Employee simpleUser = new Employee();
        simpleUser.setName("SimpleUser");
        simpleUser.setSalary(2000.0);
        simpleUser.setDepartment(hr);
        simpleUser.setRoles(new HashSet<>(List.of(viewSalary)));
        empRepo.save(simpleUser);
        System.out.println(">>> SimpleUser created.");
      }

      System.out.println(
          ">>> Seed data check complete. Use Header 'X-Logged-User: SuperAdmin' for full access.");
    };
  }
}
