package exceptionHandlers;

import exceptions.InvalidWordException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidWordHandler implements ExceptionMapper<InvalidWordException> {
    @Override
    public Response toResponse(InvalidWordException e) {
        return Response.status(Response.Status.FORBIDDEN).type("text/plain").entity(e.getMessage()).build();
    }
}
