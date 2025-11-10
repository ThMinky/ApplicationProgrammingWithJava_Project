package transport_company.daos;

import transport_company.entities.Transport;
import transport_company.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TransportDAO {

    public void create(Transport transport) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(transport);
            tx.commit();
        }
    }

    public Transport readById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT t FROM Transport t " +
                                    "LEFT JOIN FETCH t.vehicle " +
                                    "LEFT JOIN FETCH t.driver " +
                                    "WHERE t.id = :id", Transport.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }


    public List<Transport> readAllByCompanyId(Long companyId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Transport t WHERE t.company.id = :cid", Transport.class)
                    .setParameter("cid", companyId)
                    .list();
        }
    }

    public List<Transport> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Transport", Transport.class).list();
        }
    }

    public void update(Transport transport) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(transport);
            tx.commit();
        }
    }

    public void delete(Transport transport) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Transport managed = session.get(Transport.class, transport.getId());
            if (managed != null) {
                session.remove(managed);
            }
            tx.commit();
        }
    }

    public List<Transport> readAllSortedByDestination() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Transport t ORDER BY t.endLocation ASC", Transport.class)
                    .list();
        }
    }
}