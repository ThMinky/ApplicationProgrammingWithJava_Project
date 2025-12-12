package transport_company.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import transport_company.enums.ECargoType;
import transport_company.enums.ETransportSpecificationType;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "transports")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Start location cannot be null")
    @NotBlank(message = "Start location cannot be blank")
    private String startLocation;

    @NotNull(message = "End location cannot be null")
    @NotBlank(message = "End location cannot be blank")
    private String endLocation;

    @NotNull(message = "Depart time cannot be null")
    private LocalDateTime departTime;

    @NotNull(message = "Arrive time cannot be null")
    private LocalDateTime arriveTime;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Cargo type cannot be null")
    private ECargoType cargoType;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Transport specification cannot be null")
    private ETransportSpecificationType transportSpecification;

    @NotNull(message = "Weight cannot be null")
    @PositiveOrZero(message = "Weight must be positive")
    private Double weight;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Paid status cannot be null")
    private Boolean paidStatus = false;

    // //////////////////////////////////////////////////
    // Relationships
    // //////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "fk_transport_company",
            foreignKeyDefinition = "FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE"))
    @NotNull(message = "Company cannot be null")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "client_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE"
            )
    )
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Employee driver;

    // //////////////////////////////////////////////////
    // Constructors
    // //////////////////////////////////////////////////
    public Transport() {
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
    // Start Location
    // //////////////////////////////////////////////////
    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation.trim();
    }

    // //////////////////////////////////////////////////
    // End Location
    // //////////////////////////////////////////////////
    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation.trim();
    }

    // //////////////////////////////////////////////////
    // Depart Time
    // //////////////////////////////////////////////////
    public LocalDateTime getDepartTime() {
        return departTime;
    }

    public void setDepartTime(LocalDateTime departTime) {
        this.departTime = departTime;
    }

    // //////////////////////////////////////////////////
    // Arrive Time
    // //////////////////////////////////////////////////
    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(LocalDateTime arriveTime) {
        this.arriveTime = arriveTime;
    }

    // //////////////////////////////////////////////////
    // Cargo Type
    // //////////////////////////////////////////////////
    public ECargoType getCargoType() {
        return cargoType;
    }

    public void setCargoType(ECargoType cargoType) {
        this.cargoType = cargoType;
    }

    // //////////////////////////////////////////////////
    // Transport Specification
    // //////////////////////////////////////////////////
    public ETransportSpecificationType getTransportSpecification() {
        return transportSpecification;
    }

    public void setTransportSpecification(ETransportSpecificationType transportSpecification) {
        this.transportSpecification = transportSpecification;
    }

    // //////////////////////////////////////////////////
    // Weight
    // //////////////////////////////////////////////////
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    // //////////////////////////////////////////////////
    // Price
    // //////////////////////////////////////////////////
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // //////////////////////////////////////////////////
    // Paid Status
    // //////////////////////////////////////////////////
    public Boolean getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(Boolean paidStatus) {
        this.paidStatus = paidStatus;
    }

    // //////////////////////////////////////////////////
    // Client
    // //////////////////////////////////////////////////
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
    // Driver (Employee)
    // //////////////////////////////////////////////////
    public Employee getDriver() {
        return driver;
    }

    public void setDriver(Employee driver) {
        this.driver = driver;
    }

    // //////////////////////////////////////////////////
    // Vehicle
    // //////////////////////////////////////////////////
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}