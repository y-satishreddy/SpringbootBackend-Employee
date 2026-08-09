package pl.piomin.services.employee.exception;

public class DepartmentServiceNotAvailableException extends RuntimeException{
    public DepartmentServiceNotAvailableException(String message){
        super(message);
    }
}
