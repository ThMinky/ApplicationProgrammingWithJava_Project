import transport_company.daos.TransportDAO;
import transport_company.entities.Transport;
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
        List<Transport> transportsBefore = transportDAO.readAll();
        int countBefore = transportsBefore.size();

        Transport newTransport = new Transport();
        newTransport.setStartLocation("A");
        newTransport.setEndLocation("B");
        newTransport.setDepartTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        newTransport.setArriveTime(LocalDateTime.now().plusHours(5).truncatedTo(ChronoUnit.MINUTES));
        newTransport.setCargoType(ECargoType.PEOPLE);
        newTransport.setTransportSpecification(ETransportSpecificationType.PASSENGER);
        newTransport.setWeight(0.0);
        newTransport.setPrice(1500.0);
        newTransport.setCompanyById(1L);
        newTransport.setClientById(1L);
        newTransport.setVehicleById(1L);
        newTransport.setDriverById(1L);
        transportDAO.create(newTransport);

        List<Transport> transportsAfter = transportDAO.readAll();
        int countAfter = transportsAfter.size();

        assertTrue(countAfter > countBefore, "Transport creation failed");
    }

    @Test
    void testReadById() {
        Long transportId = 1L;

        Transport transport = transportDAO.readById(transportId);
        assertNotNull(transport, "Transport with ID " + transportId + " cannot be found in the database");

        System.out.println("Transport ID: " + transport.getId());
        System.out.println("Start Location: " + transport.getStartLocation());
        System.out.println("End Location: " + transport.getEndLocation());
        System.out.println("Depart Time: " + transport.getDepartTime());
        System.out.println("Arrive Time: " + transport.getArriveTime());
        System.out.println("Cargo Type: " + transport.getCargoType());
        System.out.println("Transport Specification: " + transport.getTransportSpecification());
        System.out.println("Weight: " + transport.getWeight());
        System.out.println("Price: " + transport.getPrice());
        System.out.println("Paid Status: " + transport.getPaidStatus());

        System.out.println("Company ID: " + (transport.getCompany() != null ? transport.getCompany().getId() : "null"));
        System.out.println("Client ID: " + (transport.getClient() != null ? transport.getClient().getId() : "null"));
        System.out.println("Vehicle ID: " + (transport.getVehicle() != null ? transport.getVehicle().getId() : "null"));
        System.out.println("Driver ID: " + (transport.getDriver() != null ? transport.getDriver().getId() : "null"));
    }

    @Test
    void testReadAllByCompanyId() {
        Long companyId = 1L;

        List<Transport> transports = transportDAO.readAllByCompanyId(companyId);

        if (transports.isEmpty()) {
            System.out.println("No transports found for company ID " + companyId + " in the database");
        } else {
            for (Transport transport : transports) {
                System.out.println("Transport ID: " + transport.getId());
                System.out.println("Start Location: " + transport.getStartLocation());
                System.out.println("End Location: " + transport.getEndLocation());
                System.out.println("Depart Time: " + transport.getDepartTime());
                System.out.println("Arrive Time: " + transport.getArriveTime());
                System.out.println("Cargo Type: " + transport.getCargoType());
                System.out.println("Transport Specification: " + transport.getTransportSpecification());
                System.out.println("Weight: " + transport.getWeight());
                System.out.println("Price: " + transport.getPrice());
                System.out.println("Paid Status: " + transport.getPaidStatus());

                System.out.println("Company ID: " + (transport.getCompany() != null ? transport.getCompany().getId() : "null"));
                System.out.println("Client ID: " + (transport.getClient() != null ? transport.getClient().getId() : "null"));
                System.out.println("Vehicle ID: " + (transport.getVehicle() != null ? transport.getVehicle().getId() : "null"));
                System.out.println("Driver ID: " + (transport.getDriver() != null ? transport.getDriver().getId() : "null"));

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testReadAll() {
        List<Transport> transports = transportDAO.readAll();

        if (transports.isEmpty()) {
            System.out.println("No transports found in the database");
        } else {
            for (Transport transport : transports) {
                System.out.println("Transport ID: " + transport.getId());
                System.out.println("Start Location: " + transport.getStartLocation());
                System.out.println("End Location: " + transport.getEndLocation());
                System.out.println("Depart Time: " + transport.getDepartTime());
                System.out.println("Arrive Time: " + transport.getArriveTime());
                System.out.println("Cargo Type: " + transport.getCargoType());
                System.out.println("Transport Specification: " + transport.getTransportSpecification());
                System.out.println("Weight: " + transport.getWeight());
                System.out.println("Price: " + transport.getPrice());
                System.out.println("Paid Status: " + transport.getPaidStatus());

                System.out.println("Company ID: " + (transport.getCompany() != null ? transport.getCompany().getId() : "null"));
                System.out.println("Client ID: " + (transport.getClient() != null ? transport.getClient().getId() : "null"));
                System.out.println("Vehicle ID: " + (transport.getVehicle() != null ? transport.getVehicle().getId() : "null"));
                System.out.println("Driver ID: " + (transport.getDriver() != null ? transport.getDriver().getId() : "null"));

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }

    @Test
    void testUpdate() {
        Long transportId = 1L;

        Transport transport = transportDAO.readById(transportId);

        assertNotNull(transport, "Transport with ID " + transportId + " cannot be found in the database");

        transport.setStartLocation("C");
        transport.setEndLocation("D");
        transport.setDepartTime(LocalDateTime.now().plusHours(10).truncatedTo(ChronoUnit.MINUTES));
        transport.setArriveTime(LocalDateTime.now().plusHours(15).truncatedTo(ChronoUnit.MINUTES));
        transport.setCargoType(ECargoType.GOODS);
        transport.setTransportSpecification(ETransportSpecificationType.GOODS_SPECIAL);
        transport.setWeight(100.0);
        transport.setPrice(1500.0);
        transport.setPaidStatus(true);

        assertEquals("C", transport.getStartLocation(), "Start location update was not successful");
        assertEquals("D", transport.getEndLocation(), "End location update was not successful");
        assertEquals(LocalDateTime.now().plusHours(10).truncatedTo(ChronoUnit.MINUTES), transport.getDepartTime(),
                "Depart time update was not successful");
        assertEquals(LocalDateTime.now().plusHours(15).truncatedTo(ChronoUnit.MINUTES), transport.getArriveTime(),
                "Arrive time update was not successful");
        assertEquals(ECargoType.GOODS, transport.getCargoType(), "Cargo type update was not successful");
        assertEquals(ETransportSpecificationType.GOODS_SPECIAL, transport.getTransportSpecification(), "Transport specification update was not successful");
        assertEquals(100.0, transport.getWeight(), "Weight update was not successful");
        assertEquals(1500.0, transport.getPrice(), "Price update was not successful");
        assertTrue(transport.getPaidStatus(), "Paid status update was not successful");

        transportDAO.update(transport);
    }

    @Test
    void testDelete() {
        Long transportId = 1L;

        Transport transport = transportDAO.readById(transportId);
        assertNotNull(transport, "Transport with ID " + transportId + " cannot be found in the database");

        transportDAO.delete(transport);
    }

    @Test
    void testReadAllSortedByDestination() {
        List<Transport> transports = transportDAO.readAllSortedByDestination();

        if (transports.isEmpty()) {
            System.out.println("No transports found in the database");
        } else {
            System.out.println("Transports sorted by destination (A-Z):");
            for (Transport transport : transports) {
                System.out.println("Transport ID: " + transport.getId());
                System.out.println("Start Location: " + transport.getStartLocation());
                System.out.println("End Location: " + transport.getEndLocation());
                System.out.println("Depart Time: " + transport.getDepartTime());
                System.out.println("Arrive Time: " + transport.getArriveTime());
                System.out.println("Cargo Type: " + transport.getCargoType());
                System.out.println("Transport Specification: " + transport.getTransportSpecification());
                System.out.println("Weight: " + transport.getWeight());
                System.out.println("Price: " + transport.getPrice());
                System.out.println("Paid Status: " + transport.getPaidStatus());

                System.out.println("Company ID: " + (transport.getCompany() != null ? transport.getCompany().getId() : "null"));
                System.out.println("Client ID: " + (transport.getClient() != null ? transport.getClient().getId() : "null"));
                System.out.println("Vehicle ID: " + (transport.getVehicle() != null ? transport.getVehicle().getId() : "null"));
                System.out.println("Driver ID: " + (transport.getDriver() != null ? transport.getDriver().getId() : "null"));

                System.out.println("//////////////////////////////////////////////////");
            }
        }
    }
}