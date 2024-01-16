package org.example.demo.controller;
import java.util.List;

import org.example.demo.exception.EmployeeNotFoundException;
import org.example.demo.modele.Employee;
import org.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.http.ResponseEntity;

import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {

@Autowired
  private final EmployeeRepository repository;
@Autowired
  private final EmployeeModelAssembler assembler;


  public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/employees")
  ResponseEntity<List<Employee>> all() {
//    return repository.findAll();
//
  return new ResponseEntity<List<Employee>>(repository.findAll(), HttpStatus.OK);
  }
  //Employee newEmployee = new Employee();
  //newEmployee.

  
  // end::get-aggregate-root[]

  @PostMapping("/employees")
  ResponseEntity<EntityModel<Employee>> newEmployee(@RequestBody Employee newEmployee) {
    Employee createEmployee= repository.save(newEmployee);
    return ResponseEntity //
            .created(linkTo(methodOn(EmployeeController.class).one(createEmployee.getId())).toUri()) //
            .body(assembler.toModel(createEmployee));

  }

  // Single item

  @GetMapping("/employees/{id}")
  EntityModel<Employee> one(@PathVariable Long id) {
    Employee employee = repository.findById(id) //
            .orElseThrow(() -> new EmployeeNotFoundException(id));

    return assembler.toModel(employee);
//    return repository.findById(id)
//      .orElseThrow(() -> new EmployeeNotFoundException(id));
//    Employee employee = repository.findById(id) //
//            .orElseThrow(() -> new EmployeeNotFoundException(id));
//
//    return EntityModel.of(employee, //
//            linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
//            linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
  }

  @PutMapping("/employees/{id}")
  ResponseEntity<EntityModel<Employee>> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

    Employee updatedEmployee = repository.findById(id)
      .map(employee -> {
        employee.setName(newEmployee.getName());
        employee.setRole(newEmployee.getRole());
        return repository.save(employee);
      })
      .orElseGet(() -> {
        newEmployee.setId(id);
        return repository.save(newEmployee);
      });

    EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);

    return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);
  }

  @DeleteMapping("/employees/{id}")
  ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}