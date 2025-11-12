import transport_company.EntityValidator;
import transport_company.daos.EmployeeDAO;
import transport_company.dtos.EmployeeDTO;
import transport_company.enums.EQualificationType;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeDAOTest {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @Test
    void testCreate() {
        List<EmployeeDTO> employeesBefore = employeeDAO.readAll();
        int countBefore = employeesBefore.size();

        EmployeeDTO newEmployeeDTO = new EmployeeDTO();
        newEmployeeDTO.setName("New Employee");
        newEmployeeDTO.setQualification(EQualificationType.PASSENGER);
        newEmployeeDTO.setSalary(1000.0);
        newEmployeeDTO.setCompanyId(1L);
        EntityValidator.validate(newEmployeeDTO);
        employeeDAO.create(newEmployeeDTO);

        List<EmployeeDTO> employeesAfter = employeeDAO.readAll();
        int countAfter = employeesAfter.size();

        assertTrue(countAfter > countBefore, "Employee creation failed");
    }

    @Test
    void testReadById() {
        Long employeeId = 1L;

        EmployeeDTO employeeDTO = employeeDAO.readById(employeeId);
        assertNotNull(employeeDTO, "Employee with ID " + employeeId + " cannot be found");

        System.out.println("Employee ID: " + employeeDTO.getId());
        System.out.println("Name: " + employeeDTO.getName());
        System.out.println("Qualification: " + employeeDTO.getQualification());
        System.out.println("Salary: " + employeeDTO.getSalary());

        System.out.println("Company ID: " + employeeDTO.getCompanyId());
    }

    @Test
    void testReadAll() {
        List<EmployeeDTO> employeeDTOS = employeeDAO.readAll();

        if (employeeDTOS.isEmpty()) {
            System.out.println("No employees found");
        } else {
            for (EmployeeDTO employeeDTO : employeeDTOS) {
                System.out.println("Employee ID: " + employeeDTO.getId());
                System.out.println("Name: " + employeeDTO.getName());
                System.out.println("Qualification: " + employeeDTO.getQualification());
                System.out.println("Salary: " + employeeDTO.getSalary());

                System.out.println("Company ID: " + employeeDTO.getCompanyId());

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testUpdate() {
        Long employeeId = 1L;

        EmployeeDTO employeeDTO = employeeDAO.readById(employeeId);
        assertNotNull(employeeDTO, "Employee with ID " + employeeId + " cannot be found");

        employeeDTO.setName("Updated Name");
        employeeDTO.setQualification(EQualificationType.SPECIAL_LOAD);
        employeeDTO.setSalary(1500.0);

        assertEquals("Updated Name", employeeDTO.getName(), "Employee name update was not successful");
        assertEquals(EQualificationType.SPECIAL_LOAD, employeeDTO.getQualification(), "Employee qualification update was not successful");
        assertEquals(1500.0, employeeDTO.getSalary(), "Employee salary update was not successful");

        EntityValidator.validate(employeeDTO);
        employeeDAO.update(employeeDTO);
    }

    @Test
    void testDelete() {
        Long employeeId = 1L;

        employeeDAO.delete(employeeId);
    }

    @Test
    void testReadAllSortedByQualification() {
        List<EmployeeDTO> employeeDTOS = employeeDAO.readAllSortedByQualification();

        if (employeeDTOS.isEmpty()) {
            System.out.println("No employees found");
        } else {
            System.out.println("Employees sorted by QUALIFICATION (A-Z):");
            for (EmployeeDTO employeeDTO : employeeDTOS) {
                System.out.println("Employee ID: " + employeeDTO.getId());
                System.out.println("Name: " + employeeDTO.getName());
                System.out.println("Qualification: " + employeeDTO.getQualification());
                System.out.println("Salary: " + employeeDTO.getSalary());

                System.out.println("Company ID: " + employeeDTO.getCompanyId());

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testReadAllSortedBySalary() {
        List<EmployeeDTO> employeeDTOS = employeeDAO.readAllSortedBySalary();

        if (employeeDTOS.isEmpty()) {
            System.out.println("No employees found");
        } else {
            System.out.println("Employees sorted by SALARY (lowest-highest):");
            for (EmployeeDTO employeeDTO : employeeDTOS) {
                System.out.println("Employee ID: " + employeeDTO.getId());
                System.out.println("Name: " + employeeDTO.getName());
                System.out.println("Qualification: " + employeeDTO.getQualification());
                System.out.println("Salary: " + employeeDTO.getSalary());

                System.out.println("Company ID: " + employeeDTO.getCompanyId());

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }
}