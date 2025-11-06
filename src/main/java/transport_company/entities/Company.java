package transport_company.entities;

import transport_company.util.HibernateUtil;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private double revenue;

    // Relationships
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Vehicle> vehicles = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transport> transports = new ArrayList<>();

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

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        if (revenue < 0) {
            throw new IllegalArgumentException("Revenue cannot be negative");
        }
        this.revenue = revenue;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        if (employees == null) {
            this.employees = new ArrayList<>();
        } else {
            this.employees = employees;
        }
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        if (vehicles == null) {
            this.vehicles = new ArrayList<>();
        } else {
            this.vehicles = vehicles;
        }
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        if (clients == null) {
            this.clients = new ArrayList<>();
        } else {
            this.clients = clients;
        }
    }

    public List<Transport> getTransports() {
        return transports;
    }

    public void setTransports(List<Transport> transports) {
        if (transports == null) {
            this.transports = new ArrayList<>();
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