package transport_company.daos;

import transport_company.dtos.TransportDTO;
import transport_company.entities.*;
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
            throw new IllegalArgumentException("Client must have a company ID");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Transport transport = TransportMapper.toEntity(dto);

            if (dto.getDriverId() != null) {
                Employee driver = session.get(Employee.class, dto.getDriverId());
                transport.setDriver(driver);  // fully loaded for validation
            }

            if (dto.getVehicleId() != null) {
                Vehicle vehicle = session.get(Vehicle.class, dto.getVehicleId());
                transport.setVehicle(vehicle);  // fully loaded for validation
            }

            transport.setTransportSpecification(dto.getTransportSpecification());

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
                throw new IllegalArgumentException("Transport with ID " + dto.getId() + " does not exist");
            }

            managed.setStartLocation(dto.getStartLocation());
            managed.setEndLocation(dto.getEndLocation());
            managed.setDepartTime(dto.getDepartTime());
            managed.setArriveTime(dto.getArriveTime());
            managed.setCargoType(dto.getCargoType());
            // managed.setTransportSpecification(dto.getTransportSpecification());
            managed.setWeight(dto.getWeight());
            managed.setPrice(dto.getPrice());
            managed.setPaidStatus(dto.getPaidStatus());

            if (dto.getDriverId() != null) {
                Employee driver = session.get(Employee.class, dto.getDriverId());
                managed.setDriver(driver); // fully loaded for validation
            }

            if (dto.getVehicleId() != null) {
                Vehicle vehicle = session.get(Vehicle.class, dto.getVehicleId());
                managed.setVehicle(vehicle); // fully loaded for validation
            }

            managed.setTransportSpecification(dto.getTransportSpecification());

            tx.commit();
        }
    }

    public void delete(Long transportId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Transport managed = session.get(Transport.class, transportId);
            if (managed != null) {
                session.remove(managed);
            }
            tx.commit();
        }
    }

    public List<TransportDTO> readAllSortedByDestination() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Transport> transports = session.createQuery("FROM Transport t ORDER BY t.endLocation ASC", Transport.class).list();
            return transports.stream().map(TransportMapper::toDTO).collect(Collectors.toList());
        }
    }
}