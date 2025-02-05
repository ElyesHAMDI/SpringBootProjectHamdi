package org.example.demo.driver.controller;

import org.example.demo.adapter.modele.EmployeeJpa;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class EmployeeModelAssembler implements RepresentationModelAssembler<EmployeeJpa, EntityModel<EmployeeJpa>> {


    @Override
    public EntityModel<EmployeeJpa> toModel(EmployeeJpa employee) {

        return EntityModel.of(employee, //
                linkTo(methodOn(EmployeeController.class).getOneEmployee(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getAllemplyees()).withRel("employees"));
    }
}

