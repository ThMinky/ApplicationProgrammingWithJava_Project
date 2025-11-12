package transport_company;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class EntityValidator {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static <T> void validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder("Validation failed:\n");
            for (ConstraintViolation<T> v : violations) {
                sb.append("- ").append(v.getPropertyPath())
                        .append(": ").append(v.getMessage())
                        .append("\n");
            }
            throw new IllegalArgumentException(sb.toString());
        }
    }
}