package transport_company.mappers;

import transport_company.dtos.EmployeeDTO;
import transport_company.entities.Company;
import transport_company.entities.Employee;

public class EmployeeMapper {

    public static EmployeeDTO toDTO(Employee employee) {
        if (employee == null) return null;

        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getQualification(),
                employee.getSalary(),
                employee.getCompany() != null ? employee.getCompany().getId() : null
        );
    }

    public static Employee toEntity(EmployeeDTO dto) {
        if (dto == null) return null;

        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setQualification(dto.getQualification());
        employee.setSalary(dto.getSalary());

        if (dto.getCompanyId() != null) {
            Company company = new Company();
            company.setId(dto.getCompanyId());
            employee.setCompany(company);
        }

        return employee;
    }
}