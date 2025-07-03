package io.kuz.ecom.gateway.common.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.kuz.ecom.proto.auth.AuthServiceGrpc;
import io.kuz.ecom.proto.product.ProductServiceGrpc;
import io.kuz.ecom.proto.shop.ShopServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.util.concurrent.TimeUnit;

@Configuration
public class GrpcClientConfig {

    private final GrpcChannelFactory grpcChannelFactory;

    @Autowired
    public GrpcClientConfig(GrpcChannelFactory grpcChannelFactory) {
        this.grpcChannelFactory = grpcChannelFactory;
    }

    @Bean
    public ProductServiceGrpc.ProductServiceBlockingStub productStub() {
        ManagedChannel channel = grpcChannelFactory.makeProductChannel();
        return ProductServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public AuthServiceGrpc.AuthServiceBlockingStub authStub() {
        ManagedChannel channel = grpcChannelFactory.makeAuthChannel();
        return AuthServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public ShopServiceGrpc.ShopServiceBlockingStub shopStub() {
        ManagedChannel channel = grpcChannelFactory.makeShopChannel();
        return ShopServiceGrpc.newBlockingStub(channel);
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
