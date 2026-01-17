package com.DDimov.employee_service.service.db;

import com.DDimov.employee_service.entity.Role;
import com.DDimov.employee_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
  private final RoleRepository repository;

  public List<Role> findAll() {
    return repository.findAll();
  }

  public Role findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public Role save(Role role) {
    return repository.save(role);
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }
}
