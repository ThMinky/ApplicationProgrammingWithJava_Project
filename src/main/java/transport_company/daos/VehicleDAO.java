package transport_company.daos;

import transport_company.entities.Vehicle;
import transport_company.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VehicleDAO {

    public void create(Vehicle vehicle) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(vehicle);
            tx.commit();
        }
    }

    public Vehicle readById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Vehicle.class, id);
        }
    }

    public List<Vehicle> readAllByCompanyId(Long companyId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Vehicle v WHERE v.company.id = :cid", Vehicle.class)
                    .setParameter("cid", companyId)
                    .list();
        }
    }

    public List<Vehicle> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Vehicle", Vehicle.class).list();
        }
    }

    public void update(Vehicle vehicle) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(vehicle);
            tx.commit();
        }
    }

    public void delete(Vehicle vehicle) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Vehicle managed = session.get(Vehicle.class, vehicle.getId());
            if (managed != null) {
                session.remove(managed);
            }
            tx.commit();
        }
    }
}