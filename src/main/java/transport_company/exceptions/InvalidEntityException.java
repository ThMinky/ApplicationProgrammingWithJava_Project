package transport_company.exceptions;

public class InvalidEntityException extends RuntimeException {
    public InvalidEntityException(String entityName, String message) {
        super(entityName + " is invalid: " + message);
    }
}