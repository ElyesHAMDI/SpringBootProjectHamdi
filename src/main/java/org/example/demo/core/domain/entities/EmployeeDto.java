package org.example.demo.core.domain.entities;


import java.util.Objects;

public class EmployeeDto {


    private String name;
    private String role;

    public EmployeeDto() {}

    public EmployeeDto(String name, String role) {

        this.name = name;
        this.role = role;
    }



    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof EmployeeDto))
            return false;
        EmployeeDto employeeDto = (EmployeeDto) o;
        return Objects.equals(this.name, employeeDto.name)
                && Objects.equals(this.role, employeeDto.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.role);
    }

    @Override
    public String toString() {
        return "Employee{" +  "name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
    }
}