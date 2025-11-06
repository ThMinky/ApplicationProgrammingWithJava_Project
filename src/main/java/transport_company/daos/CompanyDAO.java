package transport_company.daos;

import transport_company.entities.Company;
import transport_company.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CompanyDAO {

    public void create(Company company) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(company);
            tx.commit();
        }
    }

    public Company readById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Company.class, id);
        }
    }

    public List<Company> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Company", Company.class).list();
        }
    }

    public void update(Company company) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(company);
            tx.commit();
        }
    }

    public void delete(Company company) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Company managed = session.get(Company.class, company.getId());
            if (managed != null) {
                session.remove(managed);
            }
            tx.commit();
        }
    }

    public List<Company> readAllSortedByName() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Company c ORDER BY c.name ASC", Company.class)
                    .list();
        }
    }

    public List<Company> readAllSortedByRevenue() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Company c ORDER BY c.revenue ASC", Company.class)
                    .list();
        }
    }
}