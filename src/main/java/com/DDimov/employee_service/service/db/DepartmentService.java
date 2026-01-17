package com.DDimov.employee_service.service.db;

import com.DDimov.employee_service.entity.Department;
import com.DDimov.employee_service.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
  private final DepartmentRepository repository;

  public List<Department> findAll() {
    return repository.findAll();
  }

  public Department findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public Department save(Department dept) {
    return repository.save(dept);
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }
}
