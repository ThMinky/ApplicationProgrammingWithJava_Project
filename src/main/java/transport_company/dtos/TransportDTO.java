package transport_company.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import transport_company.enums.ECargoType;
import transport_company.enums.ETransportSpecificationType;

import java.time.LocalDateTime;

public class TransportDTO {
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

    @NotNull(message = "Cargo type cannot be null")
    private ECargoType cargoType;

    @NotNull(message = "Transport specification cannot be null")
    private ETransportSpecificationType transportSpecification;

    @NotNull(message = "Weight cannot be null")
    @PositiveOrZero(message = "Weight must be positive")
    private Double weight;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Paid status cannot be null")
    private Boolean paidStatus;

    @NotNull(message = "Client ID cannot be null")
    private Long clientId;

    @NotNull(message = "Company ID cannot be null")
    private Long companyId;

    private Long driverId;
    private Long vehicleId;

    // //////////////////////////////////////////////////
    // Constructors
    // //////////////////////////////////////////////////

    public TransportDTO() {
    }

    public TransportDTO(Long id,
                        String startLocation,
                        String endLocation,
                        LocalDateTime departTime,
                        LocalDateTime arriveTime,
                        ECargoType cargoType,
                        ETransportSpecificationType transportSpecification,
                        Double weight,
                        Double price,
                        Boolean paidStatus,
                        Long clientId,
                        Long companyId,
                        Long driverId,
                        Long vehicleId) {
        this.id = id;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.departTime = departTime;
        this.arriveTime = arriveTime;
        this.cargoType = cargoType;
        this.transportSpecification = transportSpecification;
        this.weight = weight;
        this.price = price;
        this.paidStatus = paidStatus;
        this.clientId = clientId;
        this.companyId = companyId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
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
        this.startLocation = startLocation;
    }

    // //////////////////////////////////////////////////
    // End Location
    // //////////////////////////////////////////////////
    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
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
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    // //////////////////////////////////////////////////
    // Company
    // //////////////////////////////////////////////////
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    // //////////////////////////////////////////////////
    // Driver (Employee)
    // //////////////////////////////////////////////////
    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    // //////////////////////////////////////////////////
    // Vehicle
    // //////////////////////////////////////////////////
    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
}