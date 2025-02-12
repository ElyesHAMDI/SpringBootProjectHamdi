package org.example.demo.adapter.server;


import org.example.demo.adapter.modele.EmployeeDetailJpa;
import org.example.demo.adapter.repository.AuthorizationClient;
import org.example.demo.adapter.repository.JpaEmployeeDetailsRepository;
import org.example.demo.adapter.repository.JpaEmployeeRepository;
import org.example.demo.core.domain.entities.EmployeeDetailDto;
import org.example.demo.core.domain.entities.EmployeeDto;
import org.example.demo.driver.controller.exception.NotFoundEntityException;
import org.example.demo.adapter.mapper.EmployeeMapper;
import org.example.demo.adapter.modele.EmployeeJpa;
import org.example.demo.core.repository.EmployeeAdapterRepository;
import org.example.demo.driver.controller.exception.UnauthorizedAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeAdapterService implements EmployeeAdapterRepository {

    private final JpaEmployeeDetailsRepository jpaEmployeeDetailsRepository;
    private final JpaEmployeeRepository jpaEmployeeRepository;
    private final EmployeeMapper employeeMapper;
    private final AuthorizationClient authorizationClient; // Appel du microservice des droits

    public EmployeeAdapterService(JpaEmployeeDetailsRepository jpaEmployeeDetailsRepository,
                                  JpaEmployeeRepository jpaEmployeeRepository, EmployeeMapper employeeMapper,
                                  AuthorizationClient authorizationClient) {
        this.jpaEmployeeDetailsRepository = jpaEmployeeDetailsRepository;
        this.jpaEmployeeRepository = jpaEmployeeRepository;
        this.employeeMapper = employeeMapper;
        this.authorizationClient = authorizationClient;
    }

    @Override
    public Optional<EmployeeDetailDto> getEmployesDetailById(Long employeeId, String role) {
        // Vérification des droits auprès du second microservice
        ResponseEntity<Boolean> response = authorizationClient.checkPermission(role, "details");

        if (!Boolean.TRUE.equals(response.getBody())) {
            throw new UnauthorizedAccessException("Accès refusé : l'utilisateur ID " + employeeId + " n'a pas les droits pour consulter les détails des employés");
        }

        // Si l'utilisateur a les droits, récupérer les détails de l'employé
        return jpaEmployeeDetailsRepository.findById(employeeId)
                .map(employeeMapper::toDetailDto)
                .or(() -> {
                    throw new NotFoundEntityException("Détails de l'employé avec ID " , employeeId + " non trouvés");
                });
    }




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

    @Override
    public List<EmployeeDetailDto> getAllEmployesDetails() {
        return jpaEmployeeDetailsRepository.findAll()
                .stream()
                .map(employeeMapper::toDetailDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDetailDto saveDetail(EmployeeDetailDto newEmployeeDetails) {
        try {
            EmployeeDetailJpa employeeJpa = employeeMapper.toDetailJpa(newEmployeeDetails);

            // Vérification si l'employé existe avant d'appeler get()
            EmployeeJpa employee = jpaEmployeeRepository.findById(newEmployeeDetails.getId())
                    .orElseThrow(() -> new NotFoundEntityException("Employé non trouvé", "ID: " + newEmployeeDetails.getId()));

            employeeJpa.setEmployee(employee);

            EmployeeDetailJpa savedEmployee = jpaEmployeeDetailsRepository.save(employeeJpa);
            return employeeMapper.toDetailDto(savedEmployee);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Erreur lors de la sauvegarde de l'employé", ex);
        }
    }




}
