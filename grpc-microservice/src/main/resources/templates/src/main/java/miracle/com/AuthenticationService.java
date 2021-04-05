package miracle.com;

import io.grpc.stub.StreamObserver;
import miracle.com.authenticationservice.AuthenticationGrpc;
import miracle.com.authenticationservice.AuthenticationReply;
import miracle.com.authenticationservice.AuthenticationRequest;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class AuthenticationService extends AuthenticationGrpc.AuthenticationImplBase {

    Map<String, String> scopesAndIdsMap = new HashMap<>();

    AuthenticationService(){
        scopesAndIdsMap.put("1", "test");
        scopesAndIdsMap.put("2", "test2");
        scopesAndIdsMap.put("3", "test3");
        scopesAndIdsMap.put("4", "test4");
        System.out.println(scopesAndIdsMap);
    }

    @Override
    public void authenticate(AuthenticationRequest request, StreamObserver<AuthenticationReply> responseObserver) {
        String scope = request.getScope();
        String id = request.getId();
        if(scopesAndIdsMap.get(id).equals(scope)) {
            responseObserver.onNext(AuthenticationReply.newBuilder().setAuthenticated("true").build());
            responseObserver.onCompleted();
        }
        responseObserver.onNext(AuthenticationReply.newBuilder().setAuthenticated("false").build());
        responseObserver.onCompleted();
    }
}
