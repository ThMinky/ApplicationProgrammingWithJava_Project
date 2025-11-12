package transport_company.dtos;

import java.util.HashSet;
import java.util.Set;

public class CompanyDTO {
    private Long id;
    private String name;
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
        if (clientId == null) {
            throw new IllegalArgumentException("Client ID cannot be null");
        }

        if (clientIds.contains(clientId)) {
            throw new IllegalStateException("This client ID is already in the company");
        }

        clientIds.add(clientId);
    }

    public void removeClientById(Long clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("Client ID cannot be null");
        }

        if (!clientIds.contains(clientId)) {
            throw new IllegalStateException("This client ID is not in the company");
        }

        clientIds.remove(clientId);
    }

    // //////////////////////////////////////////////////
    // Employees
    // //////////////////////////////////////////////////
    public Set<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void addEmployeeById(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }

        if (employeeIds.contains(employeeId)) {
            throw new IllegalStateException("This employee ID is already in the company");
        }

        employeeIds.add(employeeId);
    }

    public void removeEmployeeById(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }

        if (!employeeIds.contains(employeeId)) {
            throw new IllegalStateException("This employee ID is not in the company");
        }

        employeeIds.remove(employeeId);
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
            throw new IllegalStateException("This transport ID is already in the company");
        }

        transportIds.add(transportId);
    }

    public void removeTransportById(Long transportId) {
        if (transportId == null) {
            throw new IllegalArgumentException("Transport ID cannot be null");
        }

        if (!transportIds.contains(transportId)) {
            throw new IllegalStateException("This transport ID is not in the company");
        }

        transportIds.remove(transportId);
    }

    // //////////////////////////////////////////////////
    // Vehicles
    // //////////////////////////////////////////////////
    public Set<Long> getVehicleIds() {
        return vehicleIds;
    }

    public void addVehicle(Long vehicleId) {
        if (vehicleId == null) {
            throw new IllegalArgumentException("Vehicle ID cannot be null");
        }

        if (vehicleIds.contains(vehicleId)) {
            throw new IllegalStateException("This vehicle ID is already in the company");
        }

        vehicleIds.add(vehicleId);
    }

    public void removeVehicle(Long vehicleId) {
        if (vehicleId == null) {
            throw new IllegalArgumentException("Vehicle ID cannot be null");
        }

        if (!vehicleIds.contains(vehicleId)) {
            throw new IllegalStateException("This vehicle ID is not in the company");
        }

        vehicleIds.remove(vehicleId);
    }
}