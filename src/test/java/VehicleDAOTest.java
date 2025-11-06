import transport_company.daos.VehicleDAO;
import transport_company.enums.EVehicleType;
import transport_company.entities.Vehicle;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleDAOTest {

    private final VehicleDAO vehicleDAO = new VehicleDAO();

    @Test
    void testCreate() {
        List<Vehicle> vehiclesBefore = vehicleDAO.readAll();
        int countBefore = vehiclesBefore.size();

        Vehicle newVehicle = new Vehicle();
        newVehicle.setCapacity(50.0);
        newVehicle.setType(EVehicleType.TRUCK);
        newVehicle.setCompanyById(1L);
        vehicleDAO.create(newVehicle);

        List<Vehicle> vehiclesAfter = vehicleDAO.readAll();
        int countAfter = vehiclesAfter.size();

        assertTrue(countAfter > countBefore, "Vehicle creation failed");
    }

    @Test
    void testReadById() {
        Long vehicleId = 1L;

        Vehicle vehicle = vehicleDAO.readById(vehicleId);
        assertNotNull(vehicle, "Vehicle with ID " + vehicleId + " cannot be found in the database");

        System.out.println("Vehicle ID: " + vehicle.getId());
        System.out.println("Capacity: " + vehicle.getCapacity());
        System.out.println("Type: " + vehicle.getType());
        System.out.println("Company ID: " + (vehicle.getCompany() != null ? vehicle.getCompany().getId() : "null"));
    }

    @Test
    void testReadAllByCompanyId() {
        Long companyId = 1L;

        List<Vehicle> vehicles = vehicleDAO.readAllByCompanyId(companyId);

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found for company ID " + companyId + " in the database");
        } else {
            for (Vehicle vehicle : vehicles) {
                System.out.println("Vehicle ID: " + vehicle.getId());
                System.out.println("Capacity: " + vehicle.getCapacity());
                System.out.println("Type: " + vehicle.getType());
                System.out.println("Company ID: " + (vehicle.getCompany() != null ? vehicle.getCompany().getId() : "null"));

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testReadAll() {
        List<Vehicle> vehicles = vehicleDAO.readAll();

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found in the database");
        } else {
            for (Vehicle vehicle : vehicles) {
                System.out.println("Vehicle ID: " + vehicle.getId());
                System.out.println("Capacity: " + vehicle.getCapacity());
                System.out.println("Type: " + vehicle.getType());
                System.out.println("Company ID: " + (vehicle.getCompany() != null ? vehicle.getCompany().getId() : "null"));

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testUpdate() {
        Long vehicleId = 1L;

        Vehicle vehicle = vehicleDAO.readById(vehicleId);
        assertNotNull(vehicle, "Vehicle with ID " + vehicleId + " cannot be found in the database");

        vehicle.setCapacity(2000.0);
        vehicle.setType(EVehicleType.TRUCK);

        assertEquals(2000.0, vehicle.getCapacity(), "Vehicle capacity update was not successful");
        assertEquals(EVehicleType.TRUCK, vehicle.getType(), "Vehicle type update was not successful");

        vehicleDAO.update(vehicle);
    }

    @Test
    void testDelete() {
        Long vehicleId = 1L;

        Vehicle vehicle = vehicleDAO.readById(vehicleId);
        assertNotNull(vehicle, "Vehicle with ID " + vehicleId + " cannot be found in the database");

        vehicleDAO.delete(vehicle);
    }
}