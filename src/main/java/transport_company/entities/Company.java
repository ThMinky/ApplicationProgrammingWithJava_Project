package transport_company.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Company name cannot be null")
    @NotBlank(message = "Company name cannot be blank")
    @Size(max = 50, message = "Company name must be at most 50 characters")
    private String name;

    @NotNull(message = "Revenue cannot be null")
    @PositiveOrZero(message = "Revenue must be positive")
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
    public Set<Client> getClients() {
        return clients;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }

    // //////////////////////////////////////////////////
    // Employees
    // //////////////////////////////////////////////////
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    // //////////////////////////////////////////////////
    // Transports
    // //////////////////////////////////////////////////
    public Set<Transport> getTransports() {
        return transports;
    }

    public void addTransport(Transport transport) {
        transports.add(transport);
    }

    public void removeTransport(Transport transport) {
        transports.remove(transport);
    }

    // //////////////////////////////////////////////////
    // Vehicles
    // //////////////////////////////////////////////////
    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }
}