package transport_company.entities;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Client name cannot be null")
    @NotBlank(message = "Client name cannot be blank")
    @Size(max = 50, message = "Client name must be at most 50 characters")
    private String name;

    // //////////////////////////////////////////////////
    // Relationships
    // //////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "Client must have a company")
    private Company company;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Transport> transports = new HashSet<>();

    // //////////////////////////////////////////////////
    // Constructors
    // //////////////////////////////////////////////////
    public Client() {
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
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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
}