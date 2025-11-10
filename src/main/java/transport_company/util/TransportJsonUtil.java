package transport_company.util;

import transport_company.entities.Transport;
import transport_company.enums.ECargoType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import transport_company.enums.ETransportSpecificationType;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransportJsonUtil {

    private static final String FILE_PATH = "transports.json";
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(SerializationFeature.INDENT_OUTPUT);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void saveTransport(Transport transport) {
        File file = new File(FILE_PATH);
        List<SimpleTransport> transports = readTransports(file);

        transports.add(new SimpleTransport(
                transport.getId(),
                transport.getStartLocation(),
                transport.getEndLocation(),
                formatDate(transport.getDepartTime()),
                formatDate(transport.getArriveTime()),
                transport.getCargoType(),
                transport.getWeight(),
                transport.getTransportSpecification(),
                transport.getPrice(),
                transport.getPaidStatus()
        ));

        try {
            MAPPER.writeValue(file, transports);
        } catch (IOException e) {
            System.err.println("Error saving transport: " + e.getMessage());
        }
    }

    public static void printAllTransports() {
        File file = new File(FILE_PATH);
        List<SimpleTransport> transports = readTransports(file);

        if (transports.isEmpty()) {
            System.out.println("No transports found");
            return;
        }

        transports.forEach(t -> {
            System.out.println(t);
            System.out.println("\n" + " ".repeat(10) + "/".repeat(50) + "\n");
        });
    }

    private static List<SimpleTransport> readTransports(File file) {
        if (!file.exists() || file.length() == 0) return new ArrayList<>();
        try {
            return MAPPER.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            System.err.println("Error reading transports: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static String formatDate(java.time.LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(FORMATTER) : null;
    }

    private static class SimpleTransport {
        public Long id;
        public String startLocation;
        public String endLocation;
        public String departTime;
        public String arriveTime;
        public ECargoType cargoType;
        public Double weight;
        public ETransportSpecificationType transportSpecification;
        public Double price;
        public Boolean paidStatus;

        public SimpleTransport() {
        }

        public SimpleTransport(Long id, String startLocation, String endLocation,
                               String departTime, String arriveTime, ECargoType cargoType,
                               Double weight, ETransportSpecificationType transportSpecification,
                               Double price, Boolean paidStatus) {
            this.id = id;
            this.startLocation = startLocation;
            this.endLocation = endLocation;
            this.departTime = departTime;
            this.arriveTime = arriveTime;
            this.cargoType = cargoType;
            this.weight = weight;
            this.transportSpecification = transportSpecification;
            this.price = price;
            this.paidStatus = paidStatus;
        }

        @Override
        public String toString() {
            return String.format("""
                            Transport ID: %d
                            From: %s
                            To: %s
                            Depart: %s
                            Arrive: %s
                            Cargo Type: %s
                            %s
                            Price: %s
                            Paid: %s
                            """,
                    id, startLocation, endLocation, departTime, arriveTime, cargoType,
                    weight != null ? "Weight: " + weight : "Transport Specification: " + transportSpecification,
                    price, paidStatus);
        }
    }
}
