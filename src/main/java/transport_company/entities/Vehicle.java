package transport_company.entities;

import jakarta.validation.constraints.NotNull;
import transport_company.enums.EVehicleType;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double capacity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EVehicleType type; // BUS or TRUCK

    // //////////////////////////////////////////////////
    // Relationships
    // ////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",
            foreignKey = @ForeignKey(name = "fk_vehicle_company",
                    foreignKeyDefinition = "FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE"))
    private Company company;

    // //////////////////////////////////////////////////
    // Constructors
    // ////////////////////////////////////////////////
    public Vehicle() {
    }

    // //////////////////////////////////////////////////
    // Getters & Setters
    // ////////////////////////////////////////////////

    // //////////////////////////////////////////////////
    // Id
    // ////////////////////////////////////////////////
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // //////////////////////////////////////////////////
    // Capacity
    // ////////////////////////////////////////////////
    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        if (capacity == null || capacity <= 0) {
            throw new IllegalArgumentException("Vehicle capacity must be positive");
        }
        this.capacity = capacity;
    }

    // //////////////////////////////////////////////////
    // Vehicle Type
    // ////////////////////////////////////////////////
    public EVehicleType getType() {
        return type;
    }

    public void setType(EVehicleType type) {
        if (type == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null");
        }
        this.type = type;
    }

    // //////////////////////////////////////////////////
    // Company
    // ////////////////////////////////////////////////
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}