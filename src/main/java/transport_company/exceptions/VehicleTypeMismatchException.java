package transport_company.exceptions;

public class VehicleTypeMismatchException extends RuntimeException {
    public VehicleTypeMismatchException(String message) {
        super(message);
    }
}