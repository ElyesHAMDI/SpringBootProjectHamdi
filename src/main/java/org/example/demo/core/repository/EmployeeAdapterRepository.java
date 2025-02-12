package org.example.demo.core.repository;

import java.util.List;
import java.util.Optional;

import org.example.demo.core.domain.entities.EmployeeDetailDto;
import org.example.demo.core.domain.entities.EmployeeDto;

public interface EmployeeAdapterRepository  {
    Optional<EmployeeDto> findById(Long id);

    List<EmployeeDto>  getAllEmployess();

    EmployeeDto save(EmployeeDto newEmployee);

    void deleteById(Long id);

    EmployeeDto update(EmployeeDto employee);

    List<EmployeeDetailDto> getAllEmployesDetails();

    EmployeeDetailDto saveDetail(EmployeeDetailDto newEmployeeDetails);

    Optional<EmployeeDetailDto> getEmployesDetailById(Long employeeId, String role);
}