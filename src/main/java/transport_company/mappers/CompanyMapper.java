package transport_company.mappers;

import transport_company.dtos.CompanyDTO;
import transport_company.entities.Company;

import java.util.stream.Collectors;

public class CompanyMapper {

    public static CompanyDTO toDTO(Company company) {
        if (company == null) return null;

        return new CompanyDTO(
                company.getId(),
                company.getName(),
                company.getRevenue(),
                company.getClients().stream().map(c -> c.getId()).collect(Collectors.toSet()),
                company.getEmployees().stream().map(e -> e.getId()).collect(Collectors.toSet()),
                company.getTransports().stream().map(t -> t.getId()).collect(Collectors.toSet()),
                company.getVehicles().stream().map(v -> v.getId()).collect(Collectors.toSet())
        );
    }

    public static Company toEntity(CompanyDTO dto) {
        if (dto == null) return null;

        Company company = new Company();
        company.setId(dto.getId());
        company.setName(dto.getName());
        company.setRevenue(dto.getRevenue());

        return company;
    }
}