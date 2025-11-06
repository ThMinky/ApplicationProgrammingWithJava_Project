package transport_company.daos;

import transport_company.entities.Client;
import transport_company.util.HibernateUtil;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClientDAO {

    public void create(Client client) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(client);
            tx.commit();
        }
    }

    public Client readById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Client client = session.get(Client.class, id);
            if (client != null) {
                Hibernate.initialize(client.getTransports());
                Hibernate.initialize(client.getCompany());
            }
            return client;
        }
    }

    public List<Client> readAllByCompanyId(Long companyId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT c FROM Client c " +
                                    "LEFT JOIN FETCH c.company " +
                                    "LEFT JOIN FETCH c.transports " +
                                    "WHERE c.company.id = :cid", Client.class)
                    .setParameter("cid", companyId)
                    .list();
        }
    }

    public List<Client> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT c FROM Client c " +
                            "LEFT JOIN FETCH c.company " +
                            "LEFT JOIN FETCH c.transports",
                    Client.class
            ).list();
        }
    }

    public void update(Client client) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(client);
            tx.commit();
        }
    }

    public void delete(Client client) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Client managed = session.get(Client.class, client.getId());
            if (managed != null) {
                session.remove(managed);
            }
            tx.commit();
        }
    }
}