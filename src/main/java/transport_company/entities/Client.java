package transport_company.entities;

import jakarta.validation.constraints.NotNull;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(nullable = false, length = 50)
    private String name;

    // //////////////////////////////////////////////////
    // Relationships
    // ////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Transport> transports = new HashSet<>();

    // //////////////////////////////////////////////////
    // Constructors
    // ////////////////////////////////////////////////
    public Client() {
    }

    // //////////////////////////////////////////////////
    // Getters & Setters
    // ////////////////////////////////////////////////

    // //////////////////////////////////////////////////
    // Id
    // ////////////////////////////////////////////////
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // //////////////////////////////////////////////////
    // Name
    // ////////////////////////////////////////////////
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Client name cannot be null or empty");
        }
        this.name = name.trim();
    }

    // //////////////////////////////////////////////////
    // Company
    // ////////////////////////////////////////////////
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    // //////////////////////////////////////////////////
    // Transports
    // ////////////////////////////////////////////////
    public Set<Transport> getTransports() {
        return transports;
    }

    public void addTransport(Transport transport) {
        if (transport == null) {
            throw new IllegalArgumentException("Transport cannot be null");
        }

        if (transports.contains(transport)) {
            throw new IllegalStateException("This transport is already from this client");
        }

        transports.add(transport);
    }

    public void removeTransport(Transport transport) {
        if (transport == null) {
            throw new IllegalArgumentException("Transport cannot be null");
        }

        if (!transports.contains(transport)) {
            throw new IllegalStateException("This transport is not from this client");
        }

        transports.remove(transport);
    }
}