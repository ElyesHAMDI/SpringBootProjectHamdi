package org.example.demo.adapter.modele;

import javax.persistence.*;

@Entity
public class EmployeeDetailJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String address;
    private String phone;
    private String hireDate;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private EmployeeJpa employee; // Relation avec EmployeeJpa

    public EmployeeDetailJpa() {}

    public EmployeeDetailJpa(String email, String address, String phone, String hireDate, EmployeeJpa employee) {
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.hireDate = hireDate;
        this.employee = employee;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getHireDate() { return hireDate; }
    public EmployeeJpa getEmployee() { return employee; }

    public void setId(Long id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setHireDate(String hireDate) { this.hireDate = hireDate; }
    public void setEmployee(EmployeeJpa employee) { this.employee = employee; }
}
