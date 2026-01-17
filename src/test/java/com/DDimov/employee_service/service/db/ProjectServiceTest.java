package com.DDimov.employee_service.service.db;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.DDimov.employee_service.entity.Project;
import com.DDimov.employee_service.repository.ProjectRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

  @Mock private ProjectRepository projectRepository;

  @InjectMocks private ProjectService projectService;

  @Test
  void givenProjectId_whenFindById_thenReturnsProject() {
    // Given
    Long projectId = 1L;
    Project project = new Project();
    project.setId(projectId);
    project.setTitle("AI Research");
    given(projectRepository.findById(projectId)).willReturn(Optional.of(project));

    // When
    Project result = projectService.findById(projectId);

    // Then
    assertNotNull(result);
    assertEquals("AI Research", result.getTitle());
    verify(projectRepository).findById(projectId);
  }
}
