package transport_company.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import transport_company.entities.Client;
import transport_company.entities.Company;
import transport_company.entities.Transport;
import transport_company.util.HibernateUtil;

public class ClientService {

    public void payTransport(Long clientId, Long transportId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Client client = session.get(Client.class, clientId);
            if (client == null) {
                throw new IllegalArgumentException("Client not found with ID " + clientId);
            }

            Transport transport = client.getTransports().stream()
                    .filter(t -> t.getId().equals(transportId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Transport with ID " + transportId + " not found"));

            if (Boolean.TRUE.equals(transport.getPaidStatus())) {
                throw new IllegalStateException("Transport already paid");
            }

            transport.setPaidStatus(true);

            Company company = client.getCompany();
            company.setRevenue(company.getRevenue() + transport.getPrice());

            tx.commit();
        }
    }
}