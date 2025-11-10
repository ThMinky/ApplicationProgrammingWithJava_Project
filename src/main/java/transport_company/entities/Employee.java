package transport_company.entities;

import jakarta.validation.constraints.NotNull;
import transport_company.enums.EQualificationType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import org.hibernate.Session;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(nullable = false, length = 50)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EQualificationType qualification;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double salary;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",
            foreignKey = @ForeignKey(name = "fk_employee_company",
                    foreignKeyDefinition = "FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE"),
            nullable = false)
    private Company company;

    // Constructor
    public Employee() {
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public EQualificationType getQualification() {
        return qualification;
    }

    public void setQualification(EQualificationType qualification) {
        if (qualification == null) {
            throw new IllegalArgumentException("Employee qualification cannot be null");
        }
        this.qualification = qualification;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        if (salary == null || salary <= 0) {
            throw new IllegalArgumentException("Salary must be positive");
        }
        this.salary = salary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    // Helper method
    public void setCompanyById(Long companyId) {
        if (companyId == null) {
            this.company = null;
            return;
        }

        try (Session session = transport_company.util.HibernateUtil.getSessionFactory().openSession()) {
            Company company = session.get(Company.class, companyId);
            if (company == null) {
                throw new IllegalArgumentException("Company with ID " + companyId + " does not exist in the database");
            }
            this.company = company;
        }
    }
}