package pl.piomin.services.employee.exception;

public class DepartmentNotFoundException extends RuntimeException{
    public DepartmentNotFoundException (Long id){
        super("Department with id "+id+" not found");
    }
}
