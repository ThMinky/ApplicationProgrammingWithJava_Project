package transport_company.dtos;

import java.util.HashSet;
import java.util.Set;

public class ClientDTO {
    private Long id;
    private String name;
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
        if (transportId == null) {
            throw new IllegalArgumentException("Transport ID cannot be null");
        }

        if (transportIds.contains(transportId)) {
            throw new IllegalStateException("This transport ID is already from this client");
        }

        transportIds.add(transportId);
    }

    public void removeTransportById(Long transportId) {
        if (transportId == null) {
            throw new IllegalArgumentException("Transport ID cannot be null");
        }

        if (!transportIds.contains(transportId)) {
            throw new IllegalStateException("This transport ID is not from this client");
        }

        transportIds.remove(transportId);
    }
}