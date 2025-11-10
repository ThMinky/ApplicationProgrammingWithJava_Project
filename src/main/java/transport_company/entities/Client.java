package transport_company.entities;

import jakarta.validation.constraints.NotNull;
import transport_company.util.HibernateUtil;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.Session;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Transport> transports = new HashSet<>();

    // Constructor
    public Client() {
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
            throw new IllegalArgumentException("Client name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Transport> getTransports() {
        return transports;
    }

    public void setTransports(Set<Transport> transports) {
        this.transports = (transports != null) ? transports : new HashSet<>();
    }

    // Helper methods
    public void setCompanyById(Long companyId) {
        if (companyId == null) {
            this.company = null;
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Company company = session.get(Company.class, companyId);
            if (company == null) {
                throw new IllegalArgumentException("Company with ID " + companyId + " does not exist in the database");
            }
            this.company = company;
        }
    }

    public void addTransportById(Long transportId) {
        if (transportId == null) return;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transport transport = session.get(Transport.class, transportId);
            if (transport == null) {
                throw new IllegalArgumentException("Transport with ID " + transportId + " does not exist in the database");
            }

            this.transports.add(transport);
        }
    }

    public void payTransport(Long transportId, boolean paid) {
        if (transportId == null) {
            throw new IllegalArgumentException("Transport ID cannot be null");
        }

        Transport transportToPay = transports.stream()
                .filter(t -> t.getId().equals(transportId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("This client does not have a transport with ID " + transportId));

        if (transportToPay.getPaidStatus() == true) {
            throw new IllegalStateException("This transport was already paid");
        }

        transportToPay.setPaidStatus(paid);
    }

    public List<Long> getPaidTransportIds() {
        List<Long> paidIds = new ArrayList<>();
        for (Transport t : transports) {
            if (Boolean.TRUE.equals(t.getPaidStatus())) {
                paidIds.add(t.getId());
            }
        }
        return paidIds;
    }
}