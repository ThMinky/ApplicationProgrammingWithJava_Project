import transport_company.daos.VehicleDAO;
import transport_company.dtos.VehicleDTO;
import transport_company.enums.EVehicleType;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleDAOTest {

    private final VehicleDAO vehicleDAO = new VehicleDAO();

    @Test
    void testCreate() {
        List<VehicleDTO> vehiclesBefore = vehicleDAO.readAll();
        int countBefore = vehiclesBefore.size();

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setCapacity(50.0);
        vehicleDTO.setType(EVehicleType.BUS);
        vehicleDTO.setCompanyId(1L);
        vehicleDAO.create(vehicleDTO);

        List<VehicleDTO> vehiclesAfter = vehicleDAO.readAll();
        int countAfter = vehiclesAfter.size();

        assertTrue(countAfter > countBefore, "Vehicle creation failed");
    }

    @Test
    void testReadById() {
        Long vehicleId = 1L;

        VehicleDTO vehicleDTO = vehicleDAO.readById(vehicleId);
        assertNotNull(vehicleDTO, "Vehicle with ID " + vehicleId + " cannot be found in the database");

        System.out.println("Vehicle ID: " + vehicleDTO.getId());
        System.out.println("Capacity: " + vehicleDTO.getCapacity());
        System.out.println("Type: " + vehicleDTO.getType());
        System.out.println("Company ID: " + (vehicleDTO.getCompanyId() != null ? vehicleDTO.getCompanyId() : "null"));
    }

    @Test
    void testReadAll() {
        List<VehicleDTO> vehicleDTOS = vehicleDAO.readAll();

        if (vehicleDTOS.isEmpty()) {
            System.out.println("No vehicles found in the database");
        } else {
            for (VehicleDTO vehicleDTO : vehicleDTOS) {
                System.out.println("Vehicle ID: " + vehicleDTO.getId());
                System.out.println("Capacity: " + vehicleDTO.getCapacity());
                System.out.println("Type: " + vehicleDTO.getType());
                System.out.println("Company ID: " + (vehicleDTO.getCompanyId() != null ? vehicleDTO.getCompanyId() : "null"));

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testUpdate() {
        Long vehicleId = 1L;

        VehicleDTO vehicleDTO = vehicleDAO.readById(vehicleId);
        assertNotNull(vehicleDTO, "Vehicle with ID " + vehicleId + " cannot be found in the database");

        vehicleDTO.setCapacity(2000.0);
        vehicleDTO.setType(EVehicleType.TRUCK);

        assertEquals(2000.0, vehicleDTO.getCapacity(), "Vehicle capacity update was not successful");
        assertEquals(EVehicleType.TRUCK, vehicleDTO.getType(), "Vehicle type update was not successful");

        vehicleDAO.update(vehicleDTO);
    }

    @Test
    void testDelete() {
        Long vehicleId = 1L;

        vehicleDAO.delete(vehicleId);
    }
}