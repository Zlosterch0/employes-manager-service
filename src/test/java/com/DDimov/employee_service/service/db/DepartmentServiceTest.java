package com.DDimov.employee_service.service.db;

import com.DDimov.employee_service.entity.Department;
import com.DDimov.employee_service.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

  @Mock private DepartmentRepository departmentRepository;

  @InjectMocks private DepartmentService departmentService;


  @Test
  void givenDepartmentName_whenFindByName_thenReturnsDepartment() {
    // Given
    String deptName = "HR";
    Department dept = new Department();
    dept.setName(deptName);
    given(departmentRepository.findByName(deptName)).willReturn(Optional.of(dept));

    // When
    Department result = departmentService.findByName(deptName);

    // Then
    assertNotNull(result);
    assertEquals(deptName, result.getName());
  }
}
