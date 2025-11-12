package transport_company.dtos;

import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;

public class CompanyDTO {
    private Long id;

    @NotNull(message = "Company name cannot be null")
    @NotBlank(message = "Company name cannot be blank")
    @Size(max = 50, message = "Company name must be at most 50 characters")
    private String name;

    @NotNull(message = "Revenue cannot be null")
    @PositiveOrZero(message = "Revenue must be positive")
    private Double revenue;

    private Set<Long> clientIds = new HashSet<>();
    private Set<Long> employeeIds = new HashSet<>();
    private Set<Long> transportIds = new HashSet<>();
    private Set<Long> vehicleIds = new HashSet<>();

    // //////////////////////////////////////////////////
    // Constructors
    // //////////////////////////////////////////////////

    public CompanyDTO() {
    }

    public CompanyDTO(Long id,
                      String name,
                      Double revenue,
                      Set<Long> clientIds,
                      Set<Long> employeeIds,
                      Set<Long> transportIds,
                      Set<Long> vehicleIds) {
        this.id = id;
        this.name = name;
        this.revenue = revenue;
        this.clientIds = clientIds;
        this.employeeIds = employeeIds;
        this.transportIds = transportIds;
        this.vehicleIds = vehicleIds;
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
    // Revenue
    // //////////////////////////////////////////////////
    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    // //////////////////////////////////////////////////
    // Clients
    // //////////////////////////////////////////////////
    public Set<Long> getClientIds() {
        return clientIds;
    }

    public void addClientById(Long clientId) {
        clientIds.add(clientId);
    }

    public void removeClientById(Long clientId) {
        clientIds.remove(clientId);
    }

    // //////////////////////////////////////////////////
    // Employees
    // //////////////////////////////////////////////////
    public Set<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void addEmployeeById(Long employeeId) {
        employeeIds.add(employeeId);
    }

    public void removeEmployeeById(Long employeeId) {
        employeeIds.remove(employeeId);
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

    // //////////////////////////////////////////////////
    // Vehicles
    // //////////////////////////////////////////////////
    public Set<Long> getVehicleIds() {
        return vehicleIds;
    }

    public void addVehicle(Long vehicleId) {
        vehicleIds.add(vehicleId);
    }

    public void removeVehicle(Long vehicleId) {
        vehicleIds.remove(vehicleId);
    }
}