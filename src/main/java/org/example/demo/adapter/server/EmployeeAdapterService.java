package org.example.demo.adapter.server;


import org.example.demo.driver.controller.exception.NotFoundEntityException;
import org.example.demo.adapter.mapper.EmployeeMapper;
import org.example.demo.adapter.modele.EmployeeJpa;
import org.example.demo.core.domain.entities.EmployeeDto;
import org.example.demo.core.repository.EmployeeAdapterRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeAdapterService implements EmployeeAdapterRepository {

    private final JpaEmployeeRepository jpaEmployeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeAdapterService(JpaEmployeeRepository jpaEmployeeRepository, EmployeeMapper employeeMapper) {
        this.jpaEmployeeRepository = jpaEmployeeRepository;
        this.employeeMapper = employeeMapper;
    }

//    @Override
//    public Optional<EmployeeDto> findById(Long id) {
//        return jpaEmployeeRepository.findById(id)
//                .map(employeeMapper::toDto);
//    }

    @Override
    public List<EmployeeDto> getAllEmployess() {
        return jpaEmployeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto save(EmployeeDto newEmployee) {
        try {
            EmployeeJpa employeeJpa = employeeMapper.toJpa(newEmployee);
            EmployeeJpa savedEmployee = jpaEmployeeRepository.save(employeeJpa);
            return employeeMapper.toDto(savedEmployee);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Erreur lors de la sauvegarde de l'employe");
        }
    }

    @Override
    public void deleteById(Long id) {
        if (!jpaEmployeeRepository.existsById(id)) {
            throw new NotFoundEntityException("Employe avec ID " + id, " non trouve");
        }
        jpaEmployeeRepository.deleteById(id);
    }

    @Override
    public Optional<EmployeeDto> findById(Long id) {
        return Optional.ofNullable(jpaEmployeeRepository.findById(id)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new NotFoundEntityException("Employe avec ID " + id, " non trouve")));
    }



    @Override
    public EmployeeDto update(EmployeeDto employee) {
        if (!jpaEmployeeRepository.existsByName(employee.getName())) {
            throw new NotFoundEntityException("Employe avec ID " + employee.getName() + " non trouve", employee.getName());
        }
        EmployeeJpa updatedEmployee = jpaEmployeeRepository.save(employeeMapper.toJpa(employee));
        return employeeMapper.toDto(updatedEmployee);
    }
}
