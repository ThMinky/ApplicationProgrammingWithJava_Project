package transport_company.daos;

import transport_company.dtos.VehicleDTO;
import transport_company.entities.Vehicle;
import transport_company.exceptions.EntityNotFoundException;
import transport_company.exceptions.InvalidEntityException;
import transport_company.mappers.VehicleMapper;
import transport_company.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class VehicleDAO {

    public void create(VehicleDTO dto) {
        java.util.Objects.requireNonNull(dto, "Vehicle DTO cannot be null");

        if (dto.getCompanyId() == null) {
            throw new InvalidEntityException("Vehicle", "Vehicle must have a company ID");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Vehicle vehicle = VehicleMapper.toEntity(dto);

            session.persist(vehicle);
            tx.commit();
        }
    }

    public VehicleDTO readById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Vehicle vehicle = session.get(Vehicle.class, id);
            if (vehicle == null) {
                throw new EntityNotFoundException("Vehicle", id);
            }

            return VehicleMapper.toDTO(vehicle);
        }
    }

    public List<VehicleDTO> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Vehicle> vehicles = session.createQuery("FROM Vehicle", Vehicle.class).list();
            return vehicles.stream().map(VehicleMapper::toDTO).collect(Collectors.toList());
        }
    }

    public void update(VehicleDTO dto) {
        java.util.Objects.requireNonNull(dto, "Vehicle DTO cannot be null");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Vehicle managed = session.get(Vehicle.class, dto.getId());
            if (managed == null) {
                throw new EntityNotFoundException("Vehicle", dto.getId());
            }

            managed.setCapacity(dto.getCapacity());
            managed.setType(dto.getType());

            tx.commit();
        }
    }

    public void delete(Long vehicleId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Vehicle managed = session.get(Vehicle.class, vehicleId);
            if (managed == null) {
                throw new EntityNotFoundException("Vehicle", vehicleId);
            }

            session.remove(managed);

            tx.commit();
        }
    }
}