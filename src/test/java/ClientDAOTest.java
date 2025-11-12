import transport_company.EntityValidator;
import transport_company.daos.ClientDAO;
import transport_company.dtos.ClientDTO;
import transport_company.entities.Client;
import transport_company.entities.Transport;

import org.junit.jupiter.api.Test;
import transport_company.services.ClientService;
import transport_company.util.TransportJsonUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ClientDAOTest {

    private final ClientDAO clientDAO = new ClientDAO();

    @Test
    void testCreate() {
        List<ClientDTO> clientsBefore = clientDAO.readAll();
        int countBefore = clientsBefore.size();

        ClientDTO newClientDTO = new ClientDTO();
        newClientDTO.setName("New Client");
        newClientDTO.setCompanyId(1L);
        EntityValidator.validate(newClientDTO);
        clientDAO.create(newClientDTO);

        List<ClientDTO> clientsAfter = clientDAO.readAll();
        int countAfter = clientsAfter.size();

        assertTrue(countAfter > countBefore, "Client creation failed");
    }

    @Test
    void testReadById() {
        Long clientId = 1L;

        ClientDTO clientDTO = clientDAO.readById(clientId);
        assertNotNull(clientDTO, "Client with ID " + clientId + " cannot be found");

        System.out.println("Client ID: " + clientDTO.getId());
        System.out.println("Name: " + clientDTO.getName());

        System.out.println("Company ID: " + clientDTO.getCompanyId());

        System.out.print("Transport IDs: ");
        Set<Long> transportIds = clientDTO.getTransportIds();
        if (transportIds != null && !transportIds.isEmpty()) {
            System.out.println(transportIds.stream().map(Object::toString).collect(Collectors.joining(", ")));
        }
    }

    @Test
    void testReadAll() {
        List<ClientDTO> clientDTOS = clientDAO.readAll();

        if (clientDTOS.isEmpty()) {
            System.out.println("No clients found");
        } else {
            for (ClientDTO clientDTO : clientDTOS) {
                System.out.println("Client ID: " + clientDTO.getId());
                System.out.println("Name: " + clientDTO.getName());

                System.out.println("Company ID: " + clientDTO.getCompanyId());

                System.out.print("Transport IDs: ");
                Set<Long> transportIds = clientDTO.getTransportIds();
                if (transportIds != null && !transportIds.isEmpty()) {
                    System.out.println(String.join(", ",
                            transportIds.stream()
                                    .map(String::valueOf)
                                    .toArray(String[]::new)
                    ));
                } else {
                    System.out.println("No transports");
                }

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testUpdate() {
        Long clientId = 1L;

        ClientDTO clientDTO = clientDAO.readById(clientId);
        assertNotNull(clientDTO, "Client with ID " + clientId + " cannot be found");

        String newName = "Updated Name";
        clientDTO.setName(newName);

        assertEquals(newName, clientDTO.getName(), "Client name update was not successful");

        EntityValidator.validate(clientDTO);
        clientDAO.update(clientDTO);
    }

    @Test
    void testDelete() {
        Long clientId = 1L;

        clientDAO.delete(clientId);
    }

    @Test
    void testTransportPayingStatus() {
        Long clientId = 1L;
        Long transportId = 1L;

        Client client = clientDAO.readEntityById(clientId);
        assertNotNull(client, "Client with ID " + clientId + " cannot be found");

        Double oldRevenue = client.getCompany().getRevenue();

        ClientService clientService = new ClientService();
        clientService.payTransport(clientId, transportId);

        Client updatedClient = clientDAO.readEntityById(clientId);
        assertNotNull(updatedClient, "Client could not be reloaded from database");

        Transport paidTransport = updatedClient.getTransports().stream()
                .filter(t -> t.getId().equals(transportId))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        "Transport with ID " + transportId + " not found after update"
                ));

        assertTrue(paidTransport.getPaidStatus(), "Transport paid status update was not successful");

        Double newRevenue = updatedClient.getCompany().getRevenue();
        Double expectedRevenue = oldRevenue + paidTransport.getPrice();
        assertEquals(expectedRevenue, newRevenue, "Company revenue was not updated correctly");

        TransportJsonUtil.saveTransport(paidTransport);
    }

    @Test
    void testPrintAllTransportsFromJson() {
        TransportJsonUtil.printAllTransports();
    }
}