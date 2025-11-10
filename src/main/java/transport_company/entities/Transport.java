package transport_company.entities;

import transport_company.enums.ECargoType;
import transport_company.enums.EVehicleType;
import transport_company.enums.EQualificationType;
import transport_company.enums.ETransportSpecificationType;

import transport_company.util.HibernateUtil;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.hibernate.Session;
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

    // Relationships
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

    // Constructor
    public Transport() {
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        if (startLocation == null || startLocation.trim().isEmpty()) {
            throw new IllegalArgumentException("Start location cannot be null or empty");
        }
        this.startLocation = startLocation.trim();
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        if (endLocation == null || endLocation.trim().isEmpty()) {
            throw new IllegalArgumentException("End location cannot be null or empty");
        }
        this.endLocation = endLocation.trim();
    }

    public LocalDateTime getDepartTime() {
        return departTime;
    }

    public void setDepartTime(LocalDateTime departTime) {
        if (departTime == null) {
            throw new IllegalArgumentException("Depart time cannot be null");
        }
        this.departTime = departTime;
    }

    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(LocalDateTime arriveTime) {
        if (arriveTime == null) {
            throw new IllegalArgumentException("Arrive time cannot be null");
        }
        this.arriveTime = arriveTime;
    }

    public ECargoType getCargoType() {
        return cargoType;
    }

    public void setCargoType(ECargoType cargoType) {
        if (cargoType == null) {
            throw new IllegalArgumentException("Cargo type cannot be null");
        }
        this.cargoType = cargoType;
    }

    public ETransportSpecificationType getTransportSpecification() {
        return transportSpecification;
    }

    public void setTransportSpecification(ETransportSpecificationType transportSpecification) {
        if (transportSpecification == null) {
            throw new IllegalArgumentException("Specification cannot be null");
        }

        if (driver != null) validateDriverCompatibility(driver);
        if (vehicle != null) validateVehicleCompatibility(vehicle);

        this.transportSpecification = transportSpecification;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        if (cargoType == ECargoType.GOODS && (weight == null || weight <= 0)) {
            throw new IllegalArgumentException("Weight must be positive for GOODS transport");
        }
        this.weight = weight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = price;
    }

    public Boolean getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(Boolean paidStatus) {
        if (paidStatus == null) {
            throw new IllegalArgumentException("Paid status cannot be null");
        }
        this.paidStatus = paidStatus;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        validateVehicleCompatibility(vehicle);
        this.vehicle = vehicle;
    }

    public Employee getDriver() {
        return driver;
    }

    public void setDriver(Employee driver) {
        validateDriverCompatibility(driver);
        this.driver = driver;
    }

    // Helper Methods
    public void setCompanyById(Long companyId) {
        if (companyId == null) {
            this.company = null;
            return;
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Company existingCompany = session.get(Company.class, companyId);
            if (existingCompany == null) {
                throw new IllegalArgumentException("Company with ID " + companyId + " does not exist in the database");
            }
            this.company = existingCompany;
        }
    }

    public void setClientById(Long clientId) {
        if (clientId == null) {
            this.client = null;
            return;
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Client existingClient = session.get(Client.class, clientId);
            if (existingClient == null) {
                throw new IllegalArgumentException("Client with ID " + clientId + " does not exist in the database");
            }
            this.client = existingClient;
        }
    }

    public void setVehicleById(Long vehicleId) {
        if (vehicleId == null) {
            this.vehicle = null;
            return;
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Vehicle existingVehicle = session.get(Vehicle.class, vehicleId);
            if (existingVehicle == null) {
                throw new IllegalArgumentException("Vehicle with ID " + vehicleId + " does not exist in the database");
            }
            validateVehicleCompatibility(existingVehicle);
            this.vehicle = existingVehicle;
        }
    }

    public void setDriverById(Long driverId) {
        if (driverId == null) {
            this.driver = null;
            return;
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employee existingDriver = session.get(Employee.class, driverId);
            if (existingDriver == null) {
                throw new IllegalArgumentException("Driver with ID " + driverId + " does not exist in the database");
            }
            validateDriverCompatibility(existingDriver);
            this.driver = existingDriver;
        }
    }

    private void validateDriverCompatibility(Employee driverToCheck) {
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

    private void validateVehicleCompatibility(Vehicle vehicleToCheck) {
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