package transport_company.mappers;

import transport_company.dtos.ClientDTO;
import transport_company.entities.Client;
import transport_company.entities.Company;

import java.util.stream.Collectors;

public class ClientMapper {

    public static ClientDTO toDTO(Client client) {
        if (client == null) return null;

        return new ClientDTO(
                client.getId(),
                client.getName(),
                client.getCompany() != null ? client.getCompany().getId() : null,
                client.getTransports().stream().map(t -> t.getId()).collect(Collectors.toSet())
        );
    }

    public static Client toEntity(ClientDTO dto) {
        if (dto == null) return null;

        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());

        if (dto.getCompanyId() != null) {
            Company company = new Company();
            company.setId(dto.getCompanyId());
            client.setCompany(company);
        }

        return client;
    }
}