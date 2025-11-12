package transport_company.mappers;

import transport_company.dtos.TransportDTO;
import transport_company.entities.*;

public class TransportMapper {

    public static TransportDTO toDTO(Transport transport) {
        if (transport == null) return null;

        return new TransportDTO(
                transport.getId(),
                transport.getStartLocation(),
                transport.getEndLocation(),
                transport.getDepartTime(),
                transport.getArriveTime(),
                transport.getCargoType(),
                transport.getTransportSpecification(),
                transport.getWeight(),
                transport.getPrice(),
                transport.getPaidStatus(),
                transport.getCompany() != null ? transport.getCompany().getId() : null,
                transport.getClient() != null ? transport.getClient().getId() : null,
                transport.getVehicle() != null ? transport.getVehicle().getId() : null,
                transport.getDriver() != null ? transport.getDriver().getId() : null
        );
    }

    public static Transport toEntity(TransportDTO dto) {
        if (dto == null) return null;

        Transport transport = new Transport();
        transport.setId(dto.getId());
        transport.setStartLocation(dto.getStartLocation());
        transport.setEndLocation(dto.getEndLocation());
        transport.setDepartTime(dto.getDepartTime());
        transport.setArriveTime(dto.getArriveTime());
        transport.setCargoType(dto.getCargoType());
        // transport.setTransportSpecification(dto.getTransportSpecification());
        transport.setWeight(dto.getWeight());
        transport.setPrice(dto.getPrice());
        transport.setPaidStatus(dto.getPaidStatus());

        if (dto.getClientId() != null) {
            Client client = new Client();
            client.setId(dto.getClientId());
            transport.setClient(client);
        }

        if (dto.getCompanyId() != null) {
            Company company = new Company();
            company.setId(dto.getCompanyId());
            transport.setCompany(company);
        }

//        if (dto.getDriverId() != null) {
//            Employee driver = new Employee();
//            driver.setId(dto.getDriverId());
//            transport.setDriver(driver);
//        }
//
//        if (dto.getVehicleId() != null) {
//            Vehicle vehicle = new Vehicle();
//            vehicle.setId(dto.getVehicleId());
//            transport.setVehicle(vehicle);
//        }

        return transport;
    }
}