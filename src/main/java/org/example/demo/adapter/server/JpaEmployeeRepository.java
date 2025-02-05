package org.example.demo.adapter.server;

import org.example.demo.adapter.modele.EmployeeJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEmployeeRepository extends JpaRepository<EmployeeJpa, Long> {

    boolean existsByName(String name);
}
