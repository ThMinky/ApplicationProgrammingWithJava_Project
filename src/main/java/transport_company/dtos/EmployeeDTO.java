package transport_company.dtos;

import transport_company.enums.EQualificationType;

public class EmployeeDTO {
    private Long id;
    private String name;
    private EQualificationType qualification;
    private Double salary;
    private Long companyId;

    // //////////////////////////////////////////////////
    // Constructors
    // //////////////////////////////////////////////////

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long id,
                       String name,
                       EQualificationType qualification,
                       Double salary,
                       Long companyId) {
        this.id = id;
        this.name = name;
        this.qualification = qualification;
        this.salary = salary;
        this.companyId = companyId;
    }

    // //////////////////////////////////////////////////
    // Getters & Setters
    // //////////////////////////////////////////////////

    // //////////////////////////////////////////////////
    // Id
    // //////////////////////////////////////////////////
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // //////////////////////////////////////////////////
    // Name
    // //////////////////////////////////////////////////
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // //////////////////////////////////////////////////
    // Qualification
    // //////////////////////////////////////////////////
    public EQualificationType getQualification() {
        return qualification;
    }

    public void setQualification(EQualificationType qualification) {
        this.qualification = qualification;
    }

    // //////////////////////////////////////////////////
    // Salary
    // //////////////////////////////////////////////////
    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    // //////////////////////////////////////////////////
    // Company
    // //////////////////////////////////////////////////
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}