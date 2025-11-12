package transport_company.daos;

import transport_company.dtos.VehicleDTO;
import transport_company.entities.Vehicle;
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
            throw new IllegalArgumentException("Vehicle must have a company ID");
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
                throw new IllegalArgumentException("Vehicle with ID " + dto.getId() + " does not exist");
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
            if (managed != null) {
                session.remove(managed);
            }
            tx.commit();
        }
    }
}