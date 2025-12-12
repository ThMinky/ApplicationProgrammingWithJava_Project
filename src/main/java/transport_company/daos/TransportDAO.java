package transport_company.daos;

import transport_company.dtos.TransportDTO;
import transport_company.entities.*;
import transport_company.enums.EQualificationType;
import transport_company.enums.ETransportSpecificationType;
import transport_company.enums.EVehicleType;
import transport_company.exceptions.DriverQualificationMismatchException;
import transport_company.exceptions.EntityNotFoundException;
import transport_company.exceptions.InvalidEntityException;
import transport_company.exceptions.VehicleTypeMismatchException;
import transport_company.mappers.TransportMapper;
import transport_company.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class TransportDAO {

    public void create(TransportDTO dto) {
        java.util.Objects.requireNonNull(dto, "Transport DTO cannot be null");

        if (dto.getCompanyId() == null) {
            throw new InvalidEntityException("Transport", "Transport must have a company ID");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Transport transport = TransportMapper.toEntity(dto);

            Employee driver = null;
            if (dto.getDriverId() != null) {
                driver = session.get(Employee.class, dto.getDriverId());
                if (driver == null)
                    throw new EntityNotFoundException("Driver", dto.getDriverId());
                transport.setDriver(driver);
            }

            Vehicle vehicle = null;
            if (dto.getVehicleId() != null) {
                vehicle = session.get(Vehicle.class, dto.getVehicleId());
                if (vehicle == null)
                    throw new EntityNotFoundException("Vehicle", dto.getVehicleId());
                transport.setVehicle(vehicle);
            }

            validateCompatibility(dto.getTransportSpecification(), driver, vehicle);

            session.persist(transport);
            tx.commit();
        }
    }

    public TransportDTO readById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transport transport = session.createQuery(
                    "SELECT t FROM Transport t "
                            + "LEFT JOIN FETCH t.vehicle "
                            + "LEFT JOIN FETCH t.driver "
                            + "WHERE t.id = :id", Transport.class).setParameter("id", id).uniqueResult();

            if (transport == null) {
                throw new EntityNotFoundException("Transport", id);
            }

            return TransportMapper.toDTO(transport);
        }
    }

    public List<TransportDTO> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Transport> transports = session.createQuery("FROM Transport", Transport.class).list();
            return transports.stream().map(TransportMapper::toDTO).collect(Collectors.toList());
        }
    }

    public void update(TransportDTO dto) {
        java.util.Objects.requireNonNull(dto, "Transport DTO cannot be null");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Transport managed = session.get(Transport.class, dto.getId());
            if (managed == null) {
                throw new EntityNotFoundException("Transport", dto.getId());
            }

            managed.setStartLocation(dto.getStartLocation());
            managed.setEndLocation(dto.getEndLocation());
            managed.setDepartTime(dto.getDepartTime());
            managed.setArriveTime(dto.getArriveTime());
            managed.setCargoType(dto.getCargoType());
            managed.setTransportSpecification(dto.getTransportSpecification());
            managed.setWeight(dto.getWeight());
            managed.setPrice(dto.getPrice());
            managed.setPaidStatus(dto.getPaidStatus());

            Employee driver = session.get(Employee.class, dto.getDriverId());
            if (driver == null) {
                throw new EntityNotFoundException("Driver", dto.getDriverId());
            }

            Vehicle vehicle = session.get(Vehicle.class, dto.getVehicleId());
            if (vehicle == null) {
                throw new EntityNotFoundException("Vehicle", dto.getVehicleId());
            }

            validateCompatibility(dto.getTransportSpecification(), driver, vehicle);

            managed.setDriver(driver);
            managed.setVehicle(vehicle);
            managed.setTransportSpecification(dto.getTransportSpecification());

            tx.commit();
        }
    }

    public void delete(Long transportId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Transport managed = session.get(Transport.class, transportId);
            if (managed == null) {
                throw new EntityNotFoundException("Transport", transportId);
            }

            session.remove(managed);

            tx.commit();
        }
    }

    public List<TransportDTO> readAllSortedByDestination() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Transport> transports = session.createQuery("FROM Transport t ORDER BY t.endLocation ASC", Transport.class).list();
            return transports.stream().map(TransportMapper::toDTO).collect(Collectors.toList());
        }
    }

    // //////////////////////////////////////////////////
    // Helpers
    // //////////////////////////////////////////////////
    private void validateCompatibility(ETransportSpecificationType spec, Employee driver, Vehicle vehicle) {
        if (spec == null) return;

        if (driver != null) {
            EQualificationType expected = switch (spec) {
                case PASSENGER -> EQualificationType.PASSENGER;
                case GOODS_SPECIAL -> EQualificationType.SPECIAL_LOAD;
                case GOODS_HAZARDOUS -> EQualificationType.HAZARDOUS_MATERIAL;
            };
            if (driver.getQualification() != expected) {
                throw new DriverQualificationMismatchException(
                        "Driver qualification " + driver.getQualification() +
                                " does not match transport specification " + spec
                );
            }
        }

        if (vehicle != null) {
            EVehicleType expected = switch (spec) {
                case PASSENGER -> EVehicleType.BUS;
                case GOODS_SPECIAL -> EVehicleType.TRUCK;
                case GOODS_HAZARDOUS -> EVehicleType.TANK;
            };
            if (vehicle.getType() != expected) {
                throw new VehicleTypeMismatchException(
                        "Vehicle type " + vehicle.getType() +
                                " does not match transport specification " + spec
                );
            }
        }
    }
}