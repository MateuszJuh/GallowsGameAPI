package exceptionHandlers;

import exceptions.WordNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WordNotFoundHandler implements ExceptionMapper<WordNotFoundException> {
    @Override
    public Response toResponse(WordNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).type("text/plain").entity(e.getMessage()).build();
    }
}
