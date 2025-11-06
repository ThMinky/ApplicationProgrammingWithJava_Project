package transport_company.entities;

import transport_company.enums.EVehicleType;

import transport_company.util.HibernateUtil;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import org.hibernate.Session;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    @Column(nullable = false)
    private double capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EVehicleType type; // BUS or TRUCK

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",
            foreignKey = @ForeignKey(name = "fk_vehicle_company",
                    foreignKeyDefinition = "FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE"))
    private Company company;

    // Constructor
    public Vehicle() {
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Vehicle capacity must be positive");
        }
        this.capacity = capacity;
    }

    public EVehicleType getType() {
        return type;
    }

    public void setType(EVehicleType type) {
        if (type == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null");
        }
        this.type = type;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null for a Vehicle");
        }
        this.company = company;
    }

    // Helper method
    public void setCompanyById(Long companyId) {
        if (companyId == null) {
            this.company = null;
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Company existingCompany = session.get(Company.class, companyId);
            if (existingCompany == null) {
                throw new IllegalArgumentException("Company with ID " + companyId + " does not exist in the database");
            }
            this.company = existingCompany;
        }
    }
}