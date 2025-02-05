package org.example.demo.core.usecase;

import org.example.demo.core.domain.entities.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.demo.adapter.server.EmployeeAdapterService;

import java.util.List;
import java.util.Optional;

public class EmployeeUseCase {

    @Autowired
    private EmployeeAdapterService employeeAdapterService;

    public List<EmployeeDto> findAll() {

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
}
