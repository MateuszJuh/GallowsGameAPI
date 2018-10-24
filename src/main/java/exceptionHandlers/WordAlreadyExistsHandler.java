package exceptionHandlers;

import exceptions.WordAlreadyExistsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WordAlreadyExistsHandler implements ExceptionMapper<WordAlreadyExistsException> {
    @Override
    public Response toResponse(WordAlreadyExistsException e) {
        return Response.status(Response.Status.CONFLICT).type("text/plain").entity(e.getMessage()).build();
    }
}
