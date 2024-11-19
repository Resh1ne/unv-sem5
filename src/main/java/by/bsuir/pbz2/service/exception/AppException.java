package by.bsuir.pbz2.service.exception;

public class AppException extends RuntimeException {
    public AppException() {
        super();
    }

    public AppException(String massage) {
        super(massage);
    }

    public AppException(String massage, Throwable cause) {
        super(massage, cause);
    }
}