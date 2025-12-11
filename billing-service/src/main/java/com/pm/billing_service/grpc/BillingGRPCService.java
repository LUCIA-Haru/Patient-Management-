package com.pm.billing_service.grpc;

import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@GrpcService
public class BillingGRPCService extends BillingServiceGrpc.BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGRPCService.class);

    @Override
    public void createBillingAccount(billing.BillingRequest request,
                                     StreamObserver<billing.BillingResponse> responseStreamObserver){
        log.info("createBillingAccount request received {}",request.toString());

        billing.BillingResponse response = billing.BillingResponse.newBuilder()
                .setAccountId("1234")
                .setStatus("ACTIVE").build();

        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }
}
