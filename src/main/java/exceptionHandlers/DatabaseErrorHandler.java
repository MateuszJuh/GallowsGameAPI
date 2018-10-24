package exceptionHandlers;

import exceptions.DatabaseError;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DatabaseErrorHandler implements ExceptionMapper<DatabaseError> {
    @Override
    public Response toResponse(DatabaseError databaseError) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
