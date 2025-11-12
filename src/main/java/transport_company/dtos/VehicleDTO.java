package transport_company.dtos;

import transport_company.enums.EVehicleType;

public class VehicleDTO {
    private Long id;
    private Double capacity;
    private EVehicleType type;
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