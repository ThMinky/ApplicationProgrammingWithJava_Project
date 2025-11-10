import transport_company.daos.ClientDAO;
import transport_company.daos.CompanyDAO;
import transport_company.daos.TransportDAO;
import transport_company.entities.Client;
import transport_company.entities.Transport;

import org.junit.jupiter.api.Test;
import transport_company.util.TransportJsonUtil;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ClientDAOTest {

    private final ClientDAO clientDAO = new ClientDAO();

    @Test
    void testCreate() {
        List<Client> clientsBefore = clientDAO.readAll();
        int countBefore = clientsBefore.size();

        Client newClient = new Client();
        newClient.setName("New Client");
        newClient.setCompanyById(1L);
        clientDAO.create(newClient);

        List<Client> clientsAfter = clientDAO.readAll();
        int countAfter = clientsAfter.size();

        assertTrue(countAfter > countBefore, "Client creation failed");
    }

    @Test
    void testReadById() {
        Long clientId = 1L;

        Client client = clientDAO.readById(clientId);
        assertNotNull(client, "Client with ID " + clientId + " cannot be found in the database");

        System.out.println("Client ID: " + client.getId());
        System.out.println("Name: " + client.getName());

        System.out.println("Company ID: " + client.getCompany().getId());

        System.out.print("Transport IDs: ");
        Set<Transport> transports = client.getTransports();
        if (transports != null && !transports.isEmpty()) {
            int count = 0;
            for (Transport t : transports) {
                System.out.print(t.getId());
                count++;
                if (count < transports.size()) {
                    System.out.print(", ");
                }
            }
        }
    }

    @Test
    void testReadAllByCompanyId() {
        Long companyId = 1L;

        List<Client> clients = clientDAO.readAllByCompanyId(companyId);

        if (clients.isEmpty()) {
            System.out.println("No clients found for company ID " + companyId + " in the database");
        } else {
            for (Client client : clients) {
                System.out.println("Client ID: " + client.getId());
                System.out.println("Name: " + client.getName());

                System.out.println("Company ID: " + client.getCompany().getId());

                System.out.print("Transport IDs: ");
                Set<Transport> transports = client.getTransports();
                if (transports != null && !transports.isEmpty()) {
                    int count = 0;
                    for (Transport t : transports) {
                        System.out.print(t.getId());
                        count++;
                        if (count < transports.size()) {
                            System.out.print(", ");
                        }
                    }
                }
                System.out.println();
                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testReadAll() {
        List<Client> clients = clientDAO.readAll();

        if (clients.isEmpty()) {
            System.out.println("No clients found in the database");
        } else {
            for (Client client : clients) {
                System.out.println("Client ID: " + client.getId());
                System.out.println("Name: " + client.getName());

                System.out.println("Company ID: " + client.getCompany().getId());

                System.out.print("Transport IDs: ");
                Set<Transport> transports = client.getTransports();
                if (transports != null && !transports.isEmpty()) {
                    int count = 0;
                    for (Transport t : transports) {
                        System.out.print(t.getId());
                        count++;
                        if (count < transports.size()) {
                            System.out.print(", ");
                        }
                    }
                }
                System.out.println();
                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testUpdate() {
        Long clientId = 1L;

        Client client = clientDAO.readById(clientId);
        assertNotNull(client, "Client with ID " + clientId + " cannot be found in the database");

        String newName = "Updated Name";
        client.setName(newName);

        assertEquals(newName, client.getName(), "Client name update was not successful");

        clientDAO.update(client);
    }

    @Test
    void testDelete() {
        Long clientId = 1L;

        Client client = clientDAO.readById(clientId);
        assertNotNull(client, "Client with ID " + clientId + " cannot be found in the database");

        clientDAO.delete(client);
    }

    @Test
    void testTransportPayingStatus() {
        CompanyDAO companyDAO = new CompanyDAO();
        TransportDAO transportDAO = new TransportDAO();

        Long clientId = 1L;
        Long transportId = 1L;

        Client client = clientDAO.readById(clientId);

        client.payTransport(transportId, true);

        Transport paidTransport = client.getTransports().stream()
                .filter(t -> t.getId().equals(transportId))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        "Transport with ID " + transportId + " not found in the database after update"));

        assertTrue(paidTransport.getPaidStatus(), "Transport paid status update was not successful");

        double oldRevenue = client.getCompany().getRevenue();
        double newRevenue = oldRevenue + paidTransport.getPrice();

        client.getCompany().setRevenue(newRevenue);
        assertEquals(client.getCompany().getRevenue(), newRevenue, "Company revenue update was not successful");

        companyDAO.update(client.getCompany());
        transportDAO.update(paidTransport);

        TransportJsonUtil.saveTransport(paidTransport);
    }

    @Test
    void testPrintAllTransportsFromJson() {
        TransportJsonUtil.printAllTransports();
    }
}