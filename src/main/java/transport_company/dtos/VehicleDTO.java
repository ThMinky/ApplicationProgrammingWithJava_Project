package transport_company.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import transport_company.enums.EVehicleType;

public class VehicleDTO {
    private Long id;

    @NotNull(message = "Vehicle capacity cannot be null")
    @Positive(message = "Vehicle capacity must be positive")
    private Double capacity;

    @NotNull(message = "Vehicle type cannot be null")
    private EVehicleType type;

    @NotNull(message = "Vehicle must have a company ID")
    private Long companyId;

    // //////////////////////////////////////////////////
    // Constructors
    // //////////////////////////////////////////////////

    public VehicleDTO() {
    }

    public VehicleDTO(Long id,
                      Double capacity,
                      EVehicleType type,
                      Long companyId) {
        this.id = id;
        this.capacity = capacity;
        this.type = type;
        this.companyId = companyId;
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
    // Type
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
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}