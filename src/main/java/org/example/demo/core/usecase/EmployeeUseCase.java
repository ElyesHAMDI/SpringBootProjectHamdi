package org.example.demo.core.usecase;

import org.example.demo.adapter.repository.AuthorizationClient;
import org.example.demo.core.domain.entities.EmployeeDetailDto;
import org.example.demo.core.domain.entities.EmployeeDto;
import org.example.demo.core.repository.EmployeeAdapterRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public class EmployeeUseCase {

    @Autowired
    private EmployeeAdapterRepository employeeAdapterService;

    @Autowired
    private AuthorizationClient authorizationClient;

    public List<EmployeeDto> findAll() {
        String newEmployeeJson = """
            {
                "email": "test@example.com",
                "address": "123 Test St",
                "phone": "0123456789",
                "hireDate": "2023-05-01"
            }
        """;
        return employeeAdapterService.getAllEmployess();

    }

    public EmployeeDto save(EmployeeDto newEmployee) {
        return employeeAdapterService.save(newEmployee);
    }

    public Optional<EmployeeDto> findById(Long id) {
        return employeeAdapterService.findById(id);
    }

    public void deleteById(Long id) {
         employeeAdapterService.deleteById(id);
    }

    public EmployeeDto update(EmployeeDto employee, Long id) {
        return employeeAdapterService.update(employee);
    }

    public List<EmployeeDetailDto> findAllDetail() {
        return employeeAdapterService.getAllEmployesDetails();
    }

    public EmployeeDetailDto saveDetails(EmployeeDetailDto newEmployeeDetails) {
        return employeeAdapterService.saveDetail(newEmployeeDetails);
    }

    public Optional<EmployeeDetailDto> findEmplyeeDetailById(Long employeeId, String role) {
        return employeeAdapterService.getEmployesDetailById(employeeId, role);
    }

}
