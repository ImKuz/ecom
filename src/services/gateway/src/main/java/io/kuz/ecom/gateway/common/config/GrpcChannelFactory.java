package io.kuz.ecom.gateway.common.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class GrpcChannelFactory {

    private final GrpcChannelProperties grpcChannelProperties;

    @Autowired
    public GrpcChannelFactory(GrpcChannelProperties grpcChannelProperties) {
        this.grpcChannelProperties = grpcChannelProperties;
    }

    ManagedChannel makeProductChannel() {
        GrpcChannelProperties.ChannelConfig config = grpcChannelProperties.getConfig("product");
        return makeChannel(config.getHost(), config.getPort());
    }

    ManagedChannel makeAuthChannel() {
        GrpcChannelProperties.ChannelConfig config = grpcChannelProperties.getConfig("auth");
        return makeChannel(config.getHost(), config.getPort());
    }

    ManagedChannel makeShopChannel() {
        GrpcChannelProperties.ChannelConfig config = grpcChannelProperties.getConfig("shop");
        return makeChannel(config.getHost(), config.getPort());
    }

    private ManagedChannel makeChannel(String host, int port) {
        return ManagedChannelBuilder
            .forAddress(host, port)
            .usePlaintext()
            .keepAliveTime(30, TimeUnit.SECONDS)
            .keepAliveTimeout(10, TimeUnit.SECONDS)
            .keepAliveWithoutCalls(true)
            .maxInboundMessageSize(8 * 1024 * 1024)
            .build();
    }
}
