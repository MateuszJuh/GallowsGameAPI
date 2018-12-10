package exceptionHandlers;

import exceptions.InvalidTokenFormat;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidTokenFormatHandler implements ExceptionMapper<InvalidTokenFormat> {

    @Override
    public Response toResponse(InvalidTokenFormat invalidTokenFormat) {
        return Response.status(Response.Status.NOT_ACCEPTABLE).type("text/plain").entity(invalidTokenFormat.getMessage()).build();
    }
}
