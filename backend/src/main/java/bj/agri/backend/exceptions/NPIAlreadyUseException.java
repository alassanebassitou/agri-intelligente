package bj.agri.backend.exceptions;

public class NPIAlreadyUseException extends RuntimeException {
    public NPIAlreadyUseException( String npi) {
        super("This NPI is already on use: "+npi);
    }
}
