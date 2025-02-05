package org.example.demo.adapter.modele.configdatabase;


import org.example.demo.core.domain.entities.EmployeeDto;
import org.example.demo.core.repository.EmployeeAdapterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
//import org.cms.apis.hub.

@Component
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeAdapterRepository repository) {

        return args -> {
            for (EmployeeDto employeeDto : Arrays.asList(new EmployeeDto("Bilbo Baggins", "admin"), new EmployeeDto("Frodo Baggins", "user"))) {
                log.info("Preloading " + repository.save(employeeDto));
            }
        };
    }


    public void lisosans() {


        log.info("YOSR ");
        log.info("YASMINE ");
    }

}