package transport_company.daos;

import transport_company.entities.Employee;
import transport_company.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeeDAO {

    public void create(Employee employee) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(employee);
            tx.commit();
        }
    }

    public Employee readById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employee.class, id);
        }
    }

    public List<Employee> readAllByCompanyId(Long companyId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee e WHERE e.company.id = :cid", Employee.class)
                    .setParameter("cid", companyId)
                    .list();
        }
    }

    public List<Employee> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee", Employee.class).list();
        }
    }

    public void update(Employee employee) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(employee);
            tx.commit();
        }
    }

    public void delete(Employee employee) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Employee managed = session.get(Employee.class, employee.getId());
            if (managed != null) {
                session.remove(managed);
            }
            tx.commit();
        }
    }

    public List<Employee> readAllSortedByQualification() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee e ORDER BY e.qualification ASC", Employee.class)
                    .list();
        }
    }

    public List<Employee> readAllSortedBySalary() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee e ORDER BY e.salary ASC", Employee.class)
                    .list();
        }
    }
}