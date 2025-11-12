package transport_company.daos;

import transport_company.dtos.CompanyDTO;
import transport_company.entities.*;
import transport_company.mappers.CompanyMapper;
import transport_company.util.HibernateUtil;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyDAO {

    public void create(CompanyDTO dto) {
        java.util.Objects.requireNonNull(dto, "Company DTO cannot be null");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Company company = CompanyMapper.toEntity(dto);

            session.persist(company);
            tx.commit();
        }
    }

    public CompanyDTO readById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Company company = session.get(Company.class, id);
            if (company != null) {
                Hibernate.initialize(company.getClients());
                Hibernate.initialize(company.getEmployees());
                Hibernate.initialize(company.getVehicles());
                Hibernate.initialize(company.getTransports());
            }
            return CompanyMapper.toDTO(company);
        }
    }

    public List<CompanyDTO> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Company> companies = session.createQuery("FROM Company", Company.class).list();
            return companies.stream().map(CompanyMapper::toDTO).collect(Collectors.toList());
        }
    }

    public void update(CompanyDTO dto) {
        java.util.Objects.requireNonNull(dto, "Company DTO cannot be null");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Company managed = session.get(Company.class, dto.getId());
            if (managed == null) {
                throw new IllegalArgumentException("Company with ID " + dto.getId() + " does not exist");
            }

            managed.setName(dto.getName());
            managed.setRevenue(dto.getRevenue());

            tx.commit();
        }
    }

    public void delete(Long companyId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Company managed = session.get(Company.class, companyId);
            if (managed != null) {
                session.remove(managed);
            }
            tx.commit();
        }
    }

    public List<CompanyDTO> readAllSortedByName() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Company> companies = session.createQuery("FROM Company c ORDER BY c.name ASC", Company.class).list();
            return companies.stream().map(CompanyMapper::toDTO).collect(Collectors.toList());
        }
    }

    public List<CompanyDTO> readAllSortedByRevenue() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Company> companies = session.createQuery("FROM Company c ORDER BY c.revenue ASC", Company.class).list();
            return companies.stream().map(CompanyMapper::toDTO).collect(Collectors.toList());
        }
    }

    // //////////////////////////////////////////////////
    // Helpers
    // ////////////////////////////////////////////////
    public Company readByIdWithTransports(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID cannot be null");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT DISTINCT c FROM Company c " +
                                    "LEFT JOIN FETCH c.transports t " +
                                    "LEFT JOIN FETCH t.driver " +
                                    "WHERE c.id = :id", Company.class)
                    .setParameter("id", companyId)
                    .uniqueResultOptional()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Company with ID " + companyId + " does not exist"));
        }
    }
}