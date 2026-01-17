package com.DDimov.employee_service.service.db;@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;
    public List<Role> findAll() { return repository.findAll(); }
    public Role findById(Long id) { return repository.findById(id).orElse(null); }
    public Role save(Role role) { return repository.save(role); }
}