package transport_company.services;

import transport_company.daos.ClientDAO;
import transport_company.daos.TransportDAO;
import transport_company.daos.CompanyDAO;
import transport_company.entities.Client;
import transport_company.entities.Company;
import transport_company.entities.Transport;
import transport_company.mappers.CompanyMapper;
import transport_company.mappers.TransportMapper;

public class ClientService {

    private final ClientDAO clientDAO = new ClientDAO();
    private final TransportDAO transportDAO = new TransportDAO();
    private final CompanyDAO companyDAO = new CompanyDAO();

    public void payTransport(Long clientId, Long transportId) {
        Client client = clientDAO.readEntityById(clientId);
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

        transportDAO.update(TransportMapper.toDTO(transport));
        companyDAO.update(CompanyMapper.toDTO(company));
    }
}