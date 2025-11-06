package transport_company.entities;

import transport_company.enums.ECargoType;
import transport_company.enums.EVehicleType;
import transport_company.enums.EQualificationType;

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
    private LocalDateTime departTime;

    @NotNull
    private LocalDateTime arriveTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ECargoType cargoType;

    @Positive
    private Integer numberOfPeople; // Only for PEOPLE

    @Positive
    private Double weight; // Only for GOODS

    @Positive
    private double price;

    @NotNull
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
        if (vehicle != null) {
            validateVehicleForCargo(vehicle, cargoType);
        }
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        if (cargoType == ECargoType.PEOPLE && (numberOfPeople == null || numberOfPeople <= 0)) {
            throw new IllegalArgumentException("Number of people must be positive for PEOPLE transport");
        }
        this.numberOfPeople = numberOfPeople;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
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
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null for a Transport");
        }
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
        if (vehicle == null) {
            this.vehicle = null;
            return;
        }
        if (cargoType == null) {
            throw new IllegalStateException("Cargo type must be set before assigning a vehicle.");
        }
        validateVehicleForCargo(vehicle, cargoType);
        this.vehicle = vehicle;
    }

    public Employee getDriver() {
        return driver;
    }

    public void setDriver(Employee driver) {
        if (driver != null && driver.getQualification() != EQualificationType.DRIVER) {
            throw new IllegalArgumentException("Employee is not qualified as DRIVER");
        }
        this.driver = driver;
    }

    // Helper Methods
    private void validateVehicleForCargo(Vehicle vehicle, ECargoType cargoType) {
        switch (cargoType) {
            case GOODS -> {
                if (vehicle.getType() != EVehicleType.TRUCK) {
                    throw new IllegalArgumentException("Vehicle must be a TRUCK for GOODS transport");
                }
            }
            case PEOPLE -> {
                if (vehicle.getType() != EVehicleType.BUS) {
                    throw new IllegalArgumentException("Vehicle must be a BUS for PEOPLE transport");
                }
            }
        }
    }

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
            if (cargoType == null) {
                throw new IllegalStateException("Cargo type must be set before assigning a vehicle.");
            }
            validateVehicleForCargo(existingVehicle, cargoType);
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
            if (existingDriver.getQualification() != EQualificationType.DRIVER) {
                throw new IllegalArgumentException("Employee with ID " + driverId + " is not qualified as DRIVER");
            }
            this.driver = existingDriver;
        }
    }
}