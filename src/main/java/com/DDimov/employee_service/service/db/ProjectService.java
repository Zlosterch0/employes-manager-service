package com.DDimov.employee_service.service.db;@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository repository;
    public List<Project> findAll() { return repository.findAll(); }
    public Project save(Project project) { return repository.save(project); }
}