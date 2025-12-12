package transport_company.daos;

import transport_company.dtos.ClientDTO;
import transport_company.entities.Client;
import transport_company.exceptions.EntityNotFoundException;
import transport_company.exceptions.InvalidEntityException;
import transport_company.mappers.ClientMapper;
import transport_company.util.HibernateUtil;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDAO {

    public void create(ClientDTO dto) {
        java.util.Objects.requireNonNull(dto, "Client DTO cannot be null");

        if (dto.getCompanyId() == null) {
            throw new InvalidEntityException("Client", "Client must have a company ID");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Client client = ClientMapper.toEntity(dto);

            session.persist(client);
            tx.commit();
        }
    }

    public ClientDTO readById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Client client = session.get(Client.class, id);
            if (client == null) {
                throw new EntityNotFoundException("Client", id);
            }

            Hibernate.initialize(client.getTransports());
            Hibernate.initialize(client.getCompany());

            return ClientMapper.toDTO(client);
        }
    }

    public List<ClientDTO> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Client> clients = session.createQuery("SELECT DISTINCT c FROM Client c "
                    + "LEFT JOIN FETCH c.company "
                    + "LEFT JOIN FETCH c.transports", Client.class).list();

            return clients.stream().map(ClientMapper::toDTO).collect(Collectors.toList());
        }
    }

    public void update(ClientDTO dto) {
        java.util.Objects.requireNonNull(dto, "Client DTO cannot be null");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Client managed = session.get(Client.class, dto.getId());
            if (managed == null) {
                throw new EntityNotFoundException("Client", dto.getId());
            }

            managed.setName(dto.getName());

            tx.commit();
        }
    }

    public void delete(Long clientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Client managed = session.get(Client.class, clientId);
            if (managed == null) {
                throw new EntityNotFoundException("Client", clientId);
            }

            session.remove(managed);

            tx.commit();
        }
    }

    // //////////////////////////////////////////////////
    // Helpers
    // //////////////////////////////////////////////////
    public Client readEntityById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Client client = session.createQuery(
                            "SELECT c FROM Client c " +
                                    "LEFT JOIN FETCH c.transports " +
                                    "LEFT JOIN FETCH c.company " +
                                    "WHERE c.id = :id", Client.class)
                    .setParameter("id", id)
                    .uniqueResult();

            if (client == null) {
                throw new EntityNotFoundException("Client", id);
            }

            return client;
        }
    }
}