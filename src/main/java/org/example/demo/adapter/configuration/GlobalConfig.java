package org.example.demo.adapter.configuration;


import org.example.demo.core.usecase.EmployeeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {

    @Bean
    EmployeeUseCase employeeUseCase() {
        return new EmployeeUseCase();
    }
}
