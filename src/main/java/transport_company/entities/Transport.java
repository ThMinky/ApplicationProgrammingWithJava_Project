package transport_company.entities;

import transport_company.enums.ECargoType;
import transport_company.enums.EVehicleType;
import transport_company.enums.EQualificationType;
import transport_company.enums.ETransportSpecificationType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "transports")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 50, nullable = false)
    private String startLocation;

    @NotNull
    @Column(length = 50, nullable = false)
    private String endLocation;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime departTime;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime arriveTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private ECargoType cargoType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private ETransportSpecificationType transportSpecification;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double weight;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double price;

    @NotNull
    @Column(nullable = false)
    private Boolean paidStatus = false;

    // //////////////////////////////////////////////////
    // Relationships
    // ////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "fk_transport_company",
            foreignKeyDefinition = "FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE"))
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
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
    // ////////////////////////////////////////////////
    public Transport() {
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
    // Start Location
    // ////////////////////////////////////////////////
    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        if (startLocation == null || startLocation.trim().isEmpty()) {
            throw new IllegalArgumentException("Start location cannot be null or empty");
        }
        this.startLocation = startLocation.trim();
    }

    // //////////////////////////////////////////////////
    // End Location
    // ////////////////////////////////////////////////
    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        if (endLocation == null || endLocation.trim().isEmpty()) {
            throw new IllegalArgumentException("End location cannot be null or empty");
        }
        this.endLocation = endLocation.trim();
    }

    // //////////////////////////////////////////////////
    // Depart Time
    // ////////////////////////////////////////////////
    public LocalDateTime getDepartTime() {
        return departTime;
    }

    public void setDepartTime(LocalDateTime departTime) {
        if (departTime == null) {
            throw new IllegalArgumentException("Depart time cannot be null");
        }
        this.departTime = departTime;
    }

    // //////////////////////////////////////////////////
    // Arrive Time
    // ////////////////////////////////////////////////
    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(LocalDateTime arriveTime) {
        if (arriveTime == null) {
            throw new IllegalArgumentException("Arrive time cannot be null");
        }
        this.arriveTime = arriveTime;
    }

    // //////////////////////////////////////////////////
    // Cargo Type
    // ////////////////////////////////////////////////
    public ECargoType getCargoType() {
        return cargoType;
    }

    public void setCargoType(ECargoType cargoType) {
        if (cargoType == null) {
            throw new IllegalArgumentException("Cargo type cannot be null");
        }
        this.cargoType = cargoType;
    }

    // //////////////////////////////////////////////////
    // Transport Specification
    // ////////////////////////////////////////////////
    public ETransportSpecificationType getTransportSpecification() {
        return transportSpecification;
    }

    public void setTransportSpecification(ETransportSpecificationType transportSpecification) {
        if (transportSpecification == null) {
            throw new IllegalArgumentException("Specification cannot be null");
        }

        if (driver != null) validateDriverCompatibility(transportSpecification, driver);
        if (vehicle != null) validateVehicleCompatibility(transportSpecification, vehicle);

        this.transportSpecification = transportSpecification;
    }

    // //////////////////////////////////////////////////
    // Weight
    // ////////////////////////////////////////////////
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        if (cargoType == ECargoType.GOODS && (weight == null || weight <= 0)) {
            throw new IllegalArgumentException("Weight must be positive for GOODS transport");
        }
        this.weight = weight;
    }

    // //////////////////////////////////////////////////
    // Price
    // ////////////////////////////////////////////////
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = price;
    }

    // //////////////////////////////////////////////////
    // Paid Status
    // ////////////////////////////////////////////////
    public Boolean getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(Boolean paidStatus) {
        if (paidStatus == null) {
            throw new IllegalArgumentException("Paid status cannot be null");
        }
        this.paidStatus = paidStatus;
    }

    // //////////////////////////////////////////////////
    // Client
    // ////////////////////////////////////////////////
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
    // Driver (Employee)
    // ////////////////////////////////////////////////
    public Employee getDriver() {
        return driver;
    }

    public void setDriver(Employee driver) {
        this.driver = driver;
    }

    // //////////////////////////////////////////////////
    // Vehicle
    // ////////////////////////////////////////////////
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    // //////////////////////////////////////////////////
    // Helpers
    // ////////////////////////////////////////////////
    private void validateDriverCompatibility(ETransportSpecificationType transportSpecification, Employee driverToCheck) {
        if (transportSpecification == null || driverToCheck == null) return;

        EQualificationType expectedQualification = switch (transportSpecification) {
            case PASSENGER -> EQualificationType.PASSENGER;
            case GOODS_SPECIAL -> EQualificationType.SPECIAL_LOAD;
            case GOODS_HAZARDOUS -> EQualificationType.HAZARDOUS_MATERIAL;
        };

        if (driverToCheck.getQualification() != expectedQualification) {
            throw new IllegalArgumentException(
                    "Driver qualification " + driverToCheck.getQualification() +
                            " does not match transport specification " + transportSpecification
            );
        }
    }

    private void validateVehicleCompatibility(ETransportSpecificationType transportSpecification, Vehicle vehicleToCheck) {
        if (transportSpecification == null || vehicleToCheck == null) return;

        EVehicleType expectedVehicleType = switch (transportSpecification) {
            case PASSENGER -> EVehicleType.BUS;
            case GOODS_SPECIAL -> EVehicleType.TRUCK;
            case GOODS_HAZARDOUS -> EVehicleType.TANK;
        };

        if (vehicleToCheck.getType() != expectedVehicleType) {
            throw new IllegalArgumentException(
                    "Vehicle type " + vehicleToCheck.getType() +
                            " does not match transport specification " + transportSpecification
            );
        }
    }
}