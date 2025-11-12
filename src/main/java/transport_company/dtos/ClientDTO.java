package transport_company.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class ClientDTO {
    private Long id;

    @NotNull(message = "Client name cannot be null")
    @NotBlank(message = "Client name cannot be blank")
    @Size(max = 50, message = "Client name must be at most 50 characters")
    private String name;

    @NotNull(message = "Client must have a company ID")
    private Long companyId;

    private Set<Long> transportIds = new HashSet<>();

    // //////////////////////////////////////////////////
    // Constructors
    // //////////////////////////////////////////////////

    public ClientDTO() {
    }

    public ClientDTO(Long id,
                     String name,
                     Long companyId,
                     Set<Long> transportIds) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.transportIds = transportIds;
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
    // Company
    // //////////////////////////////////////////////////
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    // //////////////////////////////////////////////////
    // Transports
    // //////////////////////////////////////////////////
    public Set<Long> getTransportIds() {
        return transportIds;
    }

    public void addTransportById(Long transportId) {
        transportIds.add(transportId);
    }

    public void removeTransportById(Long transportId) {
        transportIds.remove(transportId);
    }
}