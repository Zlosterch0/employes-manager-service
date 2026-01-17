package com.DDimov.employee_service.service.db;@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository repository;
    public List<Department> findAll() { return repository.findAll(); }
}