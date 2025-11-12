package transport_company.services;

import transport_company.daos.CompanyDAO;
import transport_company.dtos.CompanyDTO;
import transport_company.entities.Company;
import transport_company.mappers.CompanyMapper;

public class CompanyService {

    private final CompanyDAO companyDAO = new CompanyDAO();

    // //////////////////////////////////////////////////
    // Regular DTO-based method
    // //////////////////////////////////////////////////
    public CompanyDTO getCompanyById(Long companyId) {
        Company company = companyDAO.readByIdWithTransports(companyId);
        return CompanyMapper.toDTO(company);
    }

    // //////////////////////////////////////////////////
    // Internal-only method
    // //////////////////////////////////////////////////
    public Company getCompanyEntityById(Long companyId) {
        return companyDAO.readByIdWithTransports(companyId);
    }
}