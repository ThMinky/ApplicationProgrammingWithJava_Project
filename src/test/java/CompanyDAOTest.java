import transport_company.daos.CompanyDAO;
import transport_company.dtos.CompanyDTO;

import org.junit.jupiter.api.Test;
import transport_company.entities.Company;
import transport_company.services.CompanyService;
import transport_company.util.CompanyReportUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyDAOTest {

    private final CompanyDAO companyDAO = new CompanyDAO();

    @Test
    void testCreate() {
        List<CompanyDTO> companiesBefore = companyDAO.readAll();
        int countBefore = companiesBefore.size();

        CompanyDTO newCompanyDTO = new CompanyDTO();
        newCompanyDTO.setName("New Company");
        newCompanyDTO.setRevenue(0.0);
        companyDAO.create(newCompanyDTO);

        List<CompanyDTO> companiesAfter = companyDAO.readAll();
        int countAfter = companiesAfter.size();

        assertTrue(countAfter > countBefore, "Company creation failed");
    }

    @Test
    void testReadById() {
        Long companyId = 1L;

        CompanyDTO companyDTO = companyDAO.readById(companyId);
        assertNotNull(companyDTO, "Company with ID " + companyId + " cannot be found");

        System.out.println("Company ID: " + companyDTO.getId());
        System.out.println("Name: " + companyDTO.getName());
        System.out.println("Revenue: " + companyDTO.getRevenue());
    }

    @Test
    void testReadAll() {
        List<CompanyDTO> companyDTOS = companyDAO.readAll();

        if (companyDTOS.isEmpty()) {
            System.out.println("No companies found");
        } else {
            for (CompanyDTO company : companyDTOS) {
                System.out.println("Company ID: " + company.getId());
                System.out.println("Name: " + company.getName());
                System.out.println("Revenue: " + company.getRevenue());

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testUpdate() {
        Long companyId = 1L;

        CompanyDTO companyDTO = companyDAO.readById(companyId);
        assertNotNull(companyDTO, "Company with ID " + companyId + " cannot be found");

        String newName = "Updated Name";
        companyDTO.setName(newName);
        companyDTO.setRevenue(2000.0);

        assertEquals(newName, companyDTO.getName(), "Company name update was not successful");
        assertEquals(2000.0, companyDTO.getRevenue(), "Company revenue update was not successful");

        companyDAO.update(companyDTO);
    }

    @Test
    void testDelete() {
        Long companyId = 1L;

        companyDAO.delete(companyId);
    }

    @Test
    void testReadAllSortedByName() {
        List<CompanyDTO> companyDTOS = companyDAO.readAllSortedByName();

        if (companyDTOS.isEmpty()) {
            System.out.println("No companies found");
        } else {
            System.out.println("Companies sorted by name (A-Z):");
            for (CompanyDTO company : companyDTOS) {
                System.out.println("Company ID: " + company.getId());
                System.out.println("Name: " + company.getName());
                System.out.println("Revenue: " + company.getRevenue());

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testReadAllSortedByRevenue() {
        List<CompanyDTO> companyDTOS = companyDAO.readAllSortedByRevenue();

        if (companyDTOS.isEmpty()) {
            System.out.println("No companies found");
        } else {
            System.out.println("Companies sorted by REVENUE (lowest - highest):");
            for (CompanyDTO company : companyDTOS) {
                System.out.println("Company ID: " + company.getId());
                System.out.println("Name: " + company.getName());
                System.out.println("Revenue: " + company.getRevenue());

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testCompanyReport() {
        Long companyId = 1L;

        CompanyService companyService = new CompanyService();
        Company company = companyService.getCompanyEntityById(companyId);

        CompanyReportUtil.printCompanyReport(company);
    }
}