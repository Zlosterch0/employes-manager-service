package com.DDimov.employee_service.service.db;

import com.DDimov.employee_service.entity.Department;
import com.DDimov.employee_service.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {
  private final DepartmentRepository repository;

  public Department findByName(String name) {
    return repository.findByName(name).orElse(null);
  }

  public Department save(Department dept) {
    return repository.save(dept);
  }
}
