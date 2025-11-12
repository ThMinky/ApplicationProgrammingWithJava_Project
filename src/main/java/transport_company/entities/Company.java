package transport_company.entities;

import jakarta.validation.constraints.NotNull;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(nullable = false, length = 50)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Double revenue;

    // //////////////////////////////////////////////////
    // Relationships
    // //////////////////////////////////////////////////
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Client> clients = new HashSet<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Transport> transports = new HashSet<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Vehicle> vehicles = new HashSet<>();

    // //////////////////////////////////////////////////
    // Constructors
    // //////////////////////////////////////////////////
    public Company() {
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
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be null or empty");
        }
        this.name = name.trim();
    }

    // //////////////////////////////////////////////////
    // Revenue
    // //////////////////////////////////////////////////
    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        if (revenue == null || revenue < 0) {
            throw new IllegalArgumentException("Revenue must be positive");
        }
        this.revenue = revenue;
    }

    // //////////////////////////////////////////////////
    // Clients
    // //////////////////////////////////////////////////
    public Set<Client> getClients() {
        return clients;
    }

    public void addClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }

        if (clients.contains(client)) {
            throw new IllegalStateException("This client is already in the company");
        }

        clients.add(client);
    }

    public void removeClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }

        if (!clients.contains(client)) {
            throw new IllegalStateException("This client is not in the company");
        }

        clients.remove(client);
    }

    // //////////////////////////////////////////////////
    // Employees
    // //////////////////////////////////////////////////
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        if (employees.contains(employee)) {
            throw new IllegalStateException("This employee is already in the company");
        }

        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        if (!employees.contains(employee)) {
            throw new IllegalStateException("This employee is not in the company");
        }

        employees.remove(employee);
    }

    // //////////////////////////////////////////////////
    // Transports
    // //////////////////////////////////////////////////
    public Set<Transport> getTransports() {
        return transports;
    }

    public void addTransport(Transport transport) {
        if (transport == null) {
            throw new IllegalArgumentException("Transport cannot be null");
        }

        if (transports.contains(transport)) {
            throw new IllegalStateException("This transport is already in the company");
        }

        transports.add(transport);
    }

    public void removeTransport(Transport transport) {
        if (transport == null) {
            throw new IllegalArgumentException("Transport cannot be null");
        }

        if (!transports.contains(transport)) {
            throw new IllegalStateException("This transport is not in the company");
        }

        transports.remove(transport);
    }

    // //////////////////////////////////////////////////
    // Vehicles
    // //////////////////////////////////////////////////
    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }

        if (vehicles.contains(vehicle)) {
            throw new IllegalStateException("This vehicle is already in the company");
        }

        vehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }

        if (!vehicles.contains(vehicle)) {
            throw new IllegalStateException("This vehicle is not in the company");
        }

        vehicles.remove(vehicle);
    }
}