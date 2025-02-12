package org.example.demo.adapter.mapper;



import org.example.demo.adapter.modele.EmployeeDetailJpa;
import org.example.demo.adapter.modele.EmployeeJpa;
import org.example.demo.core.domain.entities.EmployeeDetailDto;
import org.example.demo.core.domain.entities.EmployeeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    EmployeeDto toDto(EmployeeJpa employeeJpa);

    EmployeeJpa toJpa(EmployeeDto employeeDto);

    EmployeeDetailJpa toDetailJpa(EmployeeDetailDto newEmployeeDetails);

    EmployeeDetailDto toDetailDto(EmployeeDetailJpa savedEmployee);
}

