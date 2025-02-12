package org.example.demo.driver.controller;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.demo.core.domain.entities.EmployeeDetailDto;
import org.example.demo.core.domain.entities.EmployeeDto;

import org.example.demo.core.usecase.EmployeeUseCase;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/users")
public class EmployeeController {

@Autowired
private EmployeeUseCase employeeUseCase;

@GetMapping("/employees")
  public ResponseEntity<List<EmployeeDto>> getAllemplyees() {
    List<EmployeeDto> employees = employeeUseCase.findAll().stream()
            .map(employee -> new EmployeeDto(employee.getName(), employee.getRole() ))
            .collect(Collectors.toList());

    return ResponseEntity.ok(employees);
  }

  @PostMapping("/employees")
  public ResponseEntity<EmployeeDto> addNewEmployee(@RequestBody EmployeeDto newEmployee) {
    EmployeeDto createdEmployee = employeeUseCase.save(newEmployee);

    // Convertir Employee en EmployeeDto
    EmployeeDto employeeDto = new EmployeeDto(createdEmployee.getName(), createdEmployee.getRole());

    // Retourner une reponse avec un statut 201 et l'URI de la ressource creee
    return ResponseEntity
            .created(URI.create("/employees/" + employeeDto.getName()))
            .body(employeeDto);
  }

  @GetMapping("/employees/{id}")
  public ResponseEntity<EmployeeDto> getOneEmployee(@PathVariable Long id) {
    Optional<EmployeeDto> employee = employeeUseCase.findById(id);

    return employee.map(emp -> ResponseEntity.ok(new EmployeeDto(emp.getName(), emp.getRole())))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }



  @PutMapping("/employees/{id}")
  ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto newEmployee, @PathVariable Long id) {

    EmployeeDto createdEmployee = employeeUseCase.update(newEmployee, id);

    EmployeeDto employeeDto = new EmployeeDto(createdEmployee.getName(), createdEmployee.getRole());

    return ResponseEntity
            .created(URI.create("/employees/" + employeeDto.getName()))
            .body(employeeDto);
  }

  @DeleteMapping("/employees/{id}")
  ResponseEntity<EmployeeDto> deleteEmployee(@PathVariable Long id) {
    employeeUseCase.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/employees/details")
  ResponseEntity<List<EmployeeDetailDto>> getEllEmployeeDetails() {
    System.out.println("getEllEmployeeDetails ALL=  ");
    List<EmployeeDetailDto> employees = employeeUseCase.findAllDetail().stream()
            .map(employee -> new EmployeeDetailDto(employee.getEmail(), employee.getAddress(), employee.getPhone(), employee.getHireDate() ))
            .collect(Collectors.toList());

    return ResponseEntity.ok(employees);
  }

  @GetMapping("/employees/details/{employeeId}")
  public ResponseEntity<EmployeeDetailDto> getEmployeeDetail(@PathVariable Long employeeId) {
    // Récupérer l'employé et son rôle
    Optional<EmployeeDto> employee = employeeUseCase.findById(employeeId);

    if (employee.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    String role = employee.get().getRole(); // Récupérer le rôle depuis l'entité Employee
System.out.println("Role ======"+role);
    // Vérifier les droits et récupérer les détails
    return employeeUseCase.findEmplyeeDetailById(employeeId,  role)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }


  @PostMapping("/employees/details")
  public ResponseEntity<EmployeeDetailDto> addNewEmployee(@RequestBody EmployeeDetailDto newEmployeeDetails) {
    EmployeeDetailDto createdEmployee = employeeUseCase.saveDetails(newEmployeeDetails);
    System.out.println("employee id=  "+newEmployeeDetails.getId());

    return ResponseEntity
            .created(URI.create("/employees/" + createdEmployee.getEmail()))
            .body(createdEmployee);
  }
}