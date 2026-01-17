package com.DDimov.employee_service.service.db;

import com.DDimov.employee_service.entity.Project;
import com.DDimov.employee_service.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
  private final ProjectRepository repository;

  public List<Project> findAll() {
    return repository.findAll();
  }

  public Project save(Project project) {
    return repository.save(project);
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }
}
