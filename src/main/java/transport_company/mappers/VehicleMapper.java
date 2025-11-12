package transport_company.mappers;

import transport_company.dtos.VehicleDTO;
import transport_company.entities.Company;
import transport_company.entities.Vehicle;

public class VehicleMapper {

    public static VehicleDTO toDTO(Vehicle vehicle) {
        if (vehicle == null) return null;
        return new VehicleDTO(
                vehicle.getId(),
                vehicle.getCapacity(),
                vehicle.getType(),
                vehicle.getCompany() != null ? vehicle.getCompany().getId() : null
        );
    }

    public static Vehicle toEntity(VehicleDTO dto) {
        if (dto == null) return null;

        Vehicle vehicle = new Vehicle();
        vehicle.setId(dto.getId());
        vehicle.setCapacity(dto.getCapacity());
        vehicle.setType(dto.getType());

        if (dto.getCompanyId() != null) {
            Company company = new Company();
            company.setId(dto.getCompanyId());
            vehicle.setCompany(company);
        }

        return vehicle;
    }
}