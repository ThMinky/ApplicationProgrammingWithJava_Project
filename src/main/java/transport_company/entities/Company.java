package transport_company.entities;

import jakarta.validation.constraints.NotNull;
import transport_company.util.HibernateUtil;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.Session;

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

    // Relationships
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Vehicle> vehicles = new HashSet<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Client> clients = new HashSet<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Transport> transports = new HashSet<>();

    // Constructor
    public Company() {
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        if (revenue == null || revenue < 0) {
            throw new IllegalArgumentException("Revenue must be positive");
        }
        this.revenue = revenue;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (employees == null) {
            this.employees = new HashSet<>();
        } else {
            this.employees = employees;
        }
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        if (vehicles == null) {
            this.vehicles = new HashSet<>();
        } else {
            this.vehicles = vehicles;
        }
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        if (clients == null) {
            this.clients = new HashSet<>();
        } else {
            this.clients = clients;
        }
    }

    public Set<Transport> getTransports() {
        return transports;
    }

    public void setTransports(Set<Transport> transports) {
        if (transports == null) {
            this.transports = new HashSet<>();
        } else {
            this.transports = transports;
        }
    }

    // Helper method
    public static Company getCompanyById(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID cannot be null");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            org.hibernate.query.Query<Company> query = session.createQuery(
                    "SELECT DISTINCT c FROM Company c " +
                            "LEFT JOIN FETCH c.transports t " +
                            "LEFT JOIN FETCH t.driver " +
                            "WHERE c.id = :id",
                    Company.class
            );
            query.setParameter("id", companyId);

            Company company = query.uniqueResult();

            if (company == null) {
                throw new IllegalArgumentException("Company with ID " + companyId + " does not exist in the database");
            }

            return company;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving company with ID " + companyId + ": " + e.getMessage(), e);
        }
    }
}