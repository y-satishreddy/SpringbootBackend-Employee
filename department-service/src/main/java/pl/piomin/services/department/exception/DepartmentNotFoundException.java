package pl.piomin.services.department.exception;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException (Long Id){
        super("Department with id " + Id + " not found");
    }
}
