package transport_company.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import transport_company.enums.EVehicleType;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Vehicle capacity cannot be null")
    @Positive(message = "Vehicle capacity must be positive")
    private Double capacity;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Vehicle type cannot be null")
    private EVehicleType type;

    // //////////////////////////////////////////////////
    // Relationships
    // //////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",
            foreignKey = @ForeignKey(name = "fk_vehicle_company",
                    foreignKeyDefinition = "FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE"))
    @NotNull(message = "Vehicle must have a company")
    private Company company;

    // //////////////////////////////////////////////////
    // Constructors
    // //////////////////////////////////////////////////
    public Vehicle() {
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
    // Capacity
    // //////////////////////////////////////////////////
    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    // //////////////////////////////////////////////////
    // Vehicle Type
    // //////////////////////////////////////////////////
    public EVehicleType getType() {
        return type;
    }

    public void setType(EVehicleType type) {
        this.type = type;
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