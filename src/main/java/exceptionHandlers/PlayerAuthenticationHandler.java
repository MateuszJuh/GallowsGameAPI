package exceptionHandlers;


import exceptions.PlayerAuthenticationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PlayerAuthenticationHandler implements ExceptionMapper<PlayerAuthenticationException> {
    @Override
    public Response toResponse(PlayerAuthenticationException e) {
        return Response.status(Response.Status.OK).type("text/plain").entity(e.getMessage()).build();
    }
}
