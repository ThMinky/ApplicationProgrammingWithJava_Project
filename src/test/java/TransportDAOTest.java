import transport_company.daos.TransportDAO;
import transport_company.dtos.TransportDTO;
import transport_company.enums.ECargoType;
import transport_company.enums.ETransportSpecificationType;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransportDAOTest {

    private final TransportDAO transportDAO = new TransportDAO();

    @Test
    void testCreate() {
        List<TransportDTO> transportsBefore = transportDAO.readAll();
        int countBefore = transportsBefore.size();

        TransportDTO transportDTO = new TransportDTO();
        transportDTO.setStartLocation("A");
        transportDTO.setEndLocation("B");
        transportDTO.setDepartTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        transportDTO.setArriveTime(LocalDateTime.now().plusHours(5).truncatedTo(ChronoUnit.MINUTES));
        transportDTO.setCargoType(ECargoType.PEOPLE);
        transportDTO.setTransportSpecification(ETransportSpecificationType.PASSENGER);
        transportDTO.setWeight(0.0);
        transportDTO.setPrice(1500.0);
        transportDTO.setPaidStatus(false);
        transportDTO.setCompanyId(1L);
        transportDTO.setClientId(1L);
        transportDTO.setVehicleId(1L);
        transportDTO.setDriverId(1L);
        transportDAO.create(transportDTO);

        List<TransportDTO> transportsAfter = transportDAO.readAll();
        int countAfter = transportsAfter.size();

        assertTrue(countAfter > countBefore, "Transport creation failed");
    }

    @Test
    void testReadById() {
        Long transportId = 1L;

        TransportDTO transportDTO = transportDAO.readById(transportId);
        assertNotNull(transportDTO, "Transport with ID " + transportId + " cannot be found in the database");

        System.out.println("Transport ID: " + transportDTO.getId());
        System.out.println("Start Location: " + transportDTO.getStartLocation());
        System.out.println("End Location: " + transportDTO.getEndLocation());
        System.out.println("Depart Time: " + transportDTO.getDepartTime());
        System.out.println("Arrive Time: " + transportDTO.getArriveTime());
        System.out.println("Cargo Type: " + transportDTO.getCargoType());
        System.out.println("Transport Specification: " + transportDTO.getTransportSpecification());
        System.out.println("Weight: " + transportDTO.getWeight());
        System.out.println("Price: " + transportDTO.getPrice());
        System.out.println("Paid Status: " + transportDTO.getPaidStatus());

        System.out.println("Company ID: " + (transportDTO.getCompanyId() != null ? transportDTO.getCompanyId() : "null"));
        System.out.println("Client ID: " + (transportDTO.getClientId() != null ? transportDTO.getClientId() : "null"));
        System.out.println("Vehicle ID: " + (transportDTO.getVehicleId() != null ? transportDTO.getVehicleId() : "null"));
        System.out.println("Driver ID: " + (transportDTO.getDriverId() != null ? transportDTO.getDriverId() : "null"));
    }

    @Test
    void testReadAll() {
        List<TransportDTO> transportDTOS = transportDAO.readAll();

        if (transportDTOS.isEmpty()) {
            System.out.println("No transports found in the database");
        } else {
            for (TransportDTO transportDTO : transportDTOS) {
                System.out.println("Transport ID: " + transportDTO.getId());
                System.out.println("Start Location: " + transportDTO.getStartLocation());
                System.out.println("End Location: " + transportDTO.getEndLocation());
                System.out.println("Depart Time: " + transportDTO.getDepartTime());
                System.out.println("Arrive Time: " + transportDTO.getArriveTime());
                System.out.println("Cargo Type: " + transportDTO.getCargoType());
                System.out.println("Transport Specification: " + transportDTO.getTransportSpecification());
                System.out.println("Weight: " + transportDTO.getWeight());
                System.out.println("Price: " + transportDTO.getPrice());
                System.out.println("Paid Status: " + transportDTO.getPaidStatus());

                System.out.println("Company ID: " + (transportDTO.getCompanyId() != null ? transportDTO.getCompanyId() : "null"));
                System.out.println("Client ID: " + (transportDTO.getClientId() != null ? transportDTO.getClientId() : "null"));
                System.out.println("Vehicle ID: " + (transportDTO.getVehicleId() != null ? transportDTO.getVehicleId() : "null"));
                System.out.println("Driver ID: " + (transportDTO.getDriverId() != null ? transportDTO.getDriverId() : "null"));

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testUpdate() {
        Long transportId = 1L;

        TransportDTO transportDTO = transportDAO.readById(transportId);

        assertNotNull(transportDTO, "Transport with ID " + transportId + " cannot be found in the database");

        transportDTO.setStartLocation("Sofia");
        transportDTO.setEndLocation("Varna");
        transportDTO.setDepartTime(LocalDateTime.now().plusHours(10).truncatedTo(ChronoUnit.MINUTES));
        transportDTO.setArriveTime(LocalDateTime.now().plusHours(15).truncatedTo(ChronoUnit.MINUTES));
        transportDTO.setCargoType(ECargoType.GOODS);
        transportDTO.setTransportSpecification(ETransportSpecificationType.GOODS_SPECIAL);
        transportDTO.setWeight(100.0);
        transportDTO.setPrice(1500.0);
        transportDTO.setPaidStatus(true);

        // Change driver and vehicle
        transportDTO.setDriverId(2L);
        transportDTO.setVehicleId(2L);

        assertEquals("Sofia", transportDTO.getStartLocation(), "Start location update was not successful");
        assertEquals("Varna", transportDTO.getEndLocation(), "End location update was not successful");
        assertEquals(LocalDateTime.now().plusHours(10).truncatedTo(ChronoUnit.MINUTES), transportDTO.getDepartTime(),
                "Depart time update was not successful");
        assertEquals(LocalDateTime.now().plusHours(15).truncatedTo(ChronoUnit.MINUTES), transportDTO.getArriveTime(),
                "Arrive time update was not successful");
        assertEquals(ECargoType.GOODS, transportDTO.getCargoType(), "Cargo type update was not successful");
        assertEquals(ETransportSpecificationType.GOODS_SPECIAL, transportDTO.getTransportSpecification(), "Transport specification update was not successful");
        assertEquals(100.0, transportDTO.getWeight(), "Weight update was not successful");
        assertEquals(1500.0, transportDTO.getPrice(), "Price update was not successful");
        assertTrue(transportDTO.getPaidStatus(), "Paid status update was not successful");

        transportDAO.update(transportDTO);
    }

    @Test
    void testDelete() {
        Long transportId = 1L;

        transportDAO.delete(transportId);
    }

    @Test
    void testReadAllSortedByDestination() {
        List<TransportDTO> transportDTOS = transportDAO.readAllSortedByDestination();

        if (transportDTOS.isEmpty()) {
            System.out.println("No transports found in the database");
        } else {
            System.out.println("Transports sorted by destination (A-Z):");
            for (TransportDTO transportDTO : transportDTOS) {
                System.out.println("Transport ID: " + transportDTO.getId());
                System.out.println("Start Location: " + transportDTO.getStartLocation());
                System.out.println("End Location: " + transportDTO.getEndLocation());
                System.out.println("Depart Time: " + transportDTO.getDepartTime());
                System.out.println("Arrive Time: " + transportDTO.getArriveTime());
                System.out.println("Cargo Type: " + transportDTO.getCargoType());
                System.out.println("Transport Specification: " + transportDTO.getTransportSpecification());
                System.out.println("Weight: " + transportDTO.getWeight());
                System.out.println("Price: " + transportDTO.getPrice());
                System.out.println("Paid Status: " + transportDTO.getPaidStatus());

                System.out.println("Company ID: " + (transportDTO.getCompanyId() != null ? transportDTO.getCompanyId() : "null"));
                System.out.println("Client ID: " + (transportDTO.getClientId() != null ? transportDTO.getClientId() : "null"));
                System.out.println("Vehicle ID: " + (transportDTO.getVehicleId() != null ? transportDTO.getVehicleId() : "null"));
                System.out.println("Driver ID: " + (transportDTO.getDriverId() != null ? transportDTO.getDriverId() : "null"));

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }
}