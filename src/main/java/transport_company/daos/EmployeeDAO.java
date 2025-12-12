package transport_company.daos;

import transport_company.dtos.EmployeeDTO;
import transport_company.entities.Employee;
import transport_company.exceptions.EntityNotFoundException;
import transport_company.exceptions.InvalidEntityException;
import transport_company.mappers.EmployeeMapper;
import transport_company.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDAO {

    public void create(EmployeeDTO dto) {
        java.util.Objects.requireNonNull(dto, "Employee DTO cannot be null");

        if (dto.getCompanyId() == null) {
            throw new InvalidEntityException("Employee", "Employee must have a company ID");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Employee employee = EmployeeMapper.toEntity(dto);

            session.persist(employee);
            tx.commit();
        }
    }

    public EmployeeDTO readById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employee employee = session.get(Employee.class, id);
            if (employee == null) {
                throw new EntityNotFoundException("Employee", id);
            }

            return EmployeeMapper.toDTO(employee);
        }
    }

    public List<EmployeeDTO> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Employee> employees = session.createQuery("FROM Employee", Employee.class).list();
            return employees.stream().map(EmployeeMapper::toDTO).collect(Collectors.toList());
        }
    }

    public void update(EmployeeDTO dto) {
        java.util.Objects.requireNonNull(dto, "Employee DTO cannot be null");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Employee managed = session.get(Employee.class, dto.getId());
            if (managed == null) {
                throw new EntityNotFoundException("Employee", dto.getId());
            }

            managed.setName(dto.getName());
            managed.setQualification(dto.getQualification());
            managed.setSalary(dto.getSalary());

            tx.commit();
        }
    }

    public void delete(Long employeeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Employee managed = session.get(Employee.class, employeeId);
            if (managed == null) {
                throw new EntityNotFoundException("Employee", employeeId);
            }

            session.remove(managed);

            tx.commit();
        }
    }

    public List<EmployeeDTO> readAllSortedByQualification() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Employee> employees = session.createQuery("FROM Employee e ORDER BY e.qualification ASC", Employee.class).list();
            return employees.stream().map(EmployeeMapper::toDTO).collect(Collectors.toList());
        }
    }

    public List<EmployeeDTO> readAllSortedBySalary() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Employee> employees = session.createQuery("FROM Employee e ORDER BY e.salary ASC", Employee.class).list();
            return employees.stream().map(EmployeeMapper::toDTO).collect(Collectors.toList());
        }
    }
}