package io.kuz.ecom.gateway;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.kuz.ecom.proto.auth.AuthServiceGrpc;
import io.kuz.ecom.proto.product.ProductServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.util.concurrent.TimeUnit;

@Configuration
public class GrpcClientConfig {

    @Bean
    public ManagedChannel productChannel() {
        return ManagedChannelBuilder
            .forAddress("localhost", 7001)
            .usePlaintext()
            .keepAliveTime(30, TimeUnit.SECONDS)
            .keepAliveTimeout(10, TimeUnit.SECONDS)
            .keepAliveWithoutCalls(true)
            .maxInboundMessageSize(8 * 1024 * 1024)
            .build();
    }

    @Bean
    public ManagedChannel authChannel() {
        return ManagedChannelBuilder
            .forAddress("localhost", 7003)
            .usePlaintext()
            .keepAliveTime(30, TimeUnit.SECONDS)
            .keepAliveTimeout(10, TimeUnit.SECONDS)
            .keepAliveWithoutCalls(true)
            .maxInboundMessageSize(8 * 1024 * 1024)
            .build();
    }

    @Bean
    public ProductServiceGrpc.ProductServiceBlockingStub productStub(ManagedChannel productChannel) {
        return ProductServiceGrpc.newBlockingStub(productChannel);
    }

    @Bean
    public AuthServiceGrpc.AuthServiceBlockingStub authStub(ManagedChannel authChannel) {
        return AuthServiceGrpc.newBlockingStub(authChannel);
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeClientInfo(true);
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setIncludeHeaders(false);
        filter.setMaxPayloadLength(10000);
        return filter;
    }
}
