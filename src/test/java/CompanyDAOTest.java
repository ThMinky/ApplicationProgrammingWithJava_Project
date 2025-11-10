import transport_company.daos.CompanyDAO;
import transport_company.entities.Company;

import org.junit.jupiter.api.Test;
import transport_company.util.CompanyReportUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyDAOTest {

    private final CompanyDAO companyDAO = new CompanyDAO();

    @Test
    void testCreate() {
        List<Company> companiesBefore = companyDAO.readAll();
        int countBefore = companiesBefore.size();

        Company newCompany = new Company();
        newCompany.setName("New Company");
        newCompany.setRevenue(0.0);
        companyDAO.create(newCompany);

        List<Company> companiesAfter = companyDAO.readAll();
        int countAfter = companiesAfter.size();

        assertTrue(countAfter > countBefore, "Company creation failed");
    }

    @Test
    void testReadById() {
        Long companyId = 1L;

        Company company = companyDAO.readById(companyId);
        assertNotNull(company, "Company with ID " + companyId + " cannot be found in the database");

        System.out.println("Company ID: " + company.getId());
        System.out.println("Name: " + company.getName());
        System.out.println("Revenue: " + company.getRevenue());
    }

    @Test
    void testReadAll() {
        List<Company> companies = companyDAO.readAll();

        if (companies.isEmpty()) {
            System.out.println("No companies found in the database");
        } else {
            for (Company company : companies) {
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

        Company company = companyDAO.readById(companyId);
        assertNotNull(company, "Company with ID " + companyId + " cannot be found in the database");

        String newName = "Updated Name";
        company.setName(newName);
        company.setRevenue(2000.0);

        assertEquals(newName, company.getName(), "Company name update was not successful");
        assertEquals(2000.0, company.getRevenue(), "Company revenue update was not successful");

        companyDAO.update(company);
    }

    @Test
    void testDelete() {
        Long companyId = 1L;

        Company company = companyDAO.readById(companyId);
        assertNotNull(company, "Company with ID " + companyId + " cannot be found in the database");

        companyDAO.delete(company);
    }

    @Test
    void testReadAllSortedByName() {
        List<Company> companies = companyDAO.readAllSortedByName();

        if (companies.isEmpty()) {
            System.out.println("No companies found in the database");
        } else {
            System.out.println("Companies sorted by name (A-Z):");
            for (Company company : companies) {
                System.out.println("Company ID: " + company.getId());
                System.out.println("Name: " + company.getName());
                System.out.println("Revenue: " + company.getRevenue());

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testReadAllSortedByRevenue() {
        List<Company> companies = companyDAO.readAllSortedByRevenue();

        if (companies.isEmpty()) {
            System.out.println("No companies found in the database");
        } else {
            System.out.println("Companies sorted by REVENUE (lowest - highest):");
            for (Company company : companies) {
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

        CompanyReportUtil.printCompanyReport(Company.getCompanyById(companyId));
    }
}