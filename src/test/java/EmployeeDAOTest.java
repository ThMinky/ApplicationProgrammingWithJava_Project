import transport_company.daos.EmployeeDAO;
import transport_company.entities.Employee;
import transport_company.enums.EQualificationType;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeDAOTest {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @Test
    void testCreate() {
        List<Employee> employeesBefore = employeeDAO.readAll();
        int countBefore = employeesBefore.size();

        Employee newEmployee = new Employee();
        newEmployee.setName("New Employee");
        newEmployee.setQualification(EQualificationType.PASSENGER);
        newEmployee.setSalary(1000.0);
        newEmployee.setCompanyById(1L);
        employeeDAO.create(newEmployee);

        List<Employee> employeesAfter = employeeDAO.readAll();
        int countAfter = employeesAfter.size();

        assertTrue(countAfter > countBefore, "Employee creation failed");
    }

    @Test
    void testReadById() {
        Long employeeId = 1L;

        Employee employee = employeeDAO.readById(employeeId);
        assertNotNull(employee, "Employee with ID " + employeeId + " cannot be found in the database");

        System.out.println("Employee ID: " + employee.getId());
        System.out.println("Name: " + employee.getName());
        System.out.println("Qualification: " + employee.getQualification());
        System.out.println("Salary: " + employee.getSalary());

        System.out.println("Company ID: " + employee.getCompany().getId());
    }

    @Test
    void testReadAllByCompanyId() {
        Long companyId = 1L;

        List<Employee> employees = employeeDAO.readAllByCompanyId(companyId);

        if (employees.isEmpty()) {
            System.out.println("No employees found for company ID " + companyId + " in the database");
        } else {
            for (Employee employee : employees) {
                System.out.println("Employee ID: " + employee.getId());
                System.out.println("Name: " + employee.getName());
                System.out.println("Qualification: " + employee.getQualification());
                System.out.println("Salary: " + employee.getSalary());

                System.out.println("Company ID: " + employee.getCompany().getId());

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testReadAll() {
        List<Employee> employees = employeeDAO.readAll();

        if (employees.isEmpty()) {
            System.out.println("No employees found in the database");
        } else {
            for (Employee employee : employees) {
                System.out.println("Employee ID: " + employee.getId());
                System.out.println("Name: " + employee.getName());
                System.out.println("Qualification: " + employee.getQualification());
                System.out.println("Salary: " + employee.getSalary());

                System.out.println("Company ID: " + employee.getCompany().getId());

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testUpdate() {
        Long employeeId = 1L;

        Employee employee = employeeDAO.readById(employeeId);
        assertNotNull(employee, "Employee with ID " + employeeId + " cannot be found in the database");

        employee.setName("Updated Name");
        employee.setQualification(EQualificationType.SPECIAL_LOAD);
        employee.setSalary(1500.0);

        assertEquals("Updated Name", employee.getName(), "Employee name update was not successful");
        assertEquals(EQualificationType.SPECIAL_LOAD, employee.getQualification(), "Employee qualification update was not successful");
        assertEquals(1500.0, employee.getSalary(), "Employee salary update was not successful");

        employeeDAO.update(employee);
    }

    @Test
    void testDelete() {
        Long employeeId = 1L;

        Employee employee = employeeDAO.readById(employeeId);
        assertNotNull(employee, "Employee with ID " + employeeId + " cannot be found in the database");

        employeeDAO.delete(employee);
    }

    @Test
    void testReadAllSortedByQualification() {
        List<Employee> employees = employeeDAO.readAllSortedByQualification();

        if (employees.isEmpty()) {
            System.out.println("No employees found in the database");
        } else {
            System.out.println("Employees sorted by QUALIFICATION (A-Z):");
            for (Employee employee : employees) {
                System.out.println("Employee ID: " + employee.getId());
                System.out.println("Name: " + employee.getName());
                System.out.println("Qualification: " + employee.getQualification());
                System.out.println("Salary: " + employee.getSalary());

                System.out.println("Company ID: " + employee.getCompany().getId());

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testReadAllSortedBySalary() {
        List<Employee> employees = employeeDAO.readAllSortedBySalary();

        if (employees.isEmpty()) {
            System.out.println("No employees found in the database");
        } else {
            System.out.println("Employees sorted by SALARY (lowest - highest):");
            for (Employee employee : employees) {
                System.out.println("Employee ID: " + employee.getId());
                System.out.println("Name: " + employee.getName());
                System.out.println("Qualification: " + employee.getQualification());
                System.out.println("Salary: " + employee.getSalary());

                System.out.println("Company ID: " + employee.getCompany().getId());

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }
}