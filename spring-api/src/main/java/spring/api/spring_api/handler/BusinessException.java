package spring.api.spring_api.handler;

public class BusinessException extends RuntimeException {
    private String description;

    public BusinessException(String mensage) {
        super(mensage);
    }

    public BusinessException(String message, String description) {
        super(message);
        this.description = description;
    }
}
