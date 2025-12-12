package transport_company.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import transport_company.enums.EQualificationType;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Employee name cannot be null")
    @NotBlank(message = "Employee name cannot be blank")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Employee qualification cannot be null")
    private EQualificationType qualification;

    @NotNull(message = "Salary cannot be null")
    @Positive(message = "Salary must be positive")
    private Double salary;

    // //////////////////////////////////////////////////
    // Relationships
    // //////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",
            foreignKey = @ForeignKey(name = "fk_employee_company",
                    foreignKeyDefinition = "FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE"),
            nullable = false)
    @NotNull(message = "Employee must have a company")
    private Company company;

    // //////////////////////////////////////////////////
    // Constructors
    // //////////////////////////////////////////////////
    public Employee() {
    }

    // //////////////////////////////////////////////////
    // Getters & Setters
    // //////////////////////////////////////////////////

    // //////////////////////////////////////////////////
    // Id
    // //////////////////////////////////////////////////
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // //////////////////////////////////////////////////
    // Name
    // //////////////////////////////////////////////////
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // //////////////////////////////////////////////////
    // Qualification
    // //////////////////////////////////////////////////
    public EQualificationType getQualification() {
        return qualification;
    }

    public void setQualification(EQualificationType qualification) {
        this.qualification = qualification;
    }

    // //////////////////////////////////////////////////
    // Salary
    // //////////////////////////////////////////////////
    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    // //////////////////////////////////////////////////
    // Company
    // //////////////////////////////////////////////////
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}