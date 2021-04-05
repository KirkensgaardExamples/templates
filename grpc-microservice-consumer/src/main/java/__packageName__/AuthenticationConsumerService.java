package @packageName@;

import javax.ws.rs.Path;
import io.quarkus.grpc.runtime.annotations.GrpcService;
import @packageName@.authenticationservice.AuthenticationGrpc;
import @packageName@.authenticationservice.AuthenticationRequest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1")
public class AuthenticationConsumerService {

    @Inject
    @GrpcService("authentication")
    AuthenticationGrpc.AuthenticationBlockingStub client;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBankAccountById(@HeaderParam("id") String id){
        if(client.authenticate(AuthenticationRequest.newBuilder().setId(id).setScope("test").build()).getAuthenticated().equals("true")){
            BankAccountDTO bankAccountDTO = new BankAccountDTO();
            bankAccountDTO.setAccountName(id);
            bankAccountDTO.setBalance(200);
            return Response.status(200).entity(bankAccountDTO).build();
        }
        return Response.status(403).build();
    }
}
