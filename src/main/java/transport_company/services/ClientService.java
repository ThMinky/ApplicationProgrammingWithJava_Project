package transport_company.services;

import transport_company.daos.ClientDAO;
import transport_company.daos.TransportDAO;
import transport_company.daos.CompanyDAO;
import transport_company.dtos.CompanyDTO;
import transport_company.dtos.TransportDTO;
import transport_company.entities.Client;
import transport_company.entities.Transport;
import transport_company.mappers.CompanyMapper;
import transport_company.mappers.TransportMapper;

public class ClientService {

    private final ClientDAO clientDAO = new ClientDAO();
    private final TransportDAO transportDAO = new TransportDAO();
    private final CompanyDAO companyDAO = new CompanyDAO();

    public void payTransport(Long clientId, Long transportId) {
        Client client = clientDAO.readEntityById(clientId);

        Transport transport = client.getTransports().stream()
                .filter(t -> t.getId().equals(transportId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Transport with ID " + transportId + " not found"));

        if (Boolean.TRUE.equals(transport.getPaidStatus())) {
            throw new IllegalStateException("Transport already paid");
        }

        transport.setPaidStatus(true);

        Double newRevenue = client.getCompany().getRevenue() + transport.getPrice();
        client.getCompany().setRevenue(newRevenue);

        TransportDTO transportDTO = TransportMapper.toDTO(transport);
        transportDAO.update(transportDTO);

        CompanyDTO companyDTO = CompanyMapper.toDTO(client.getCompany());
        companyDAO.update(companyDTO);
    }
}