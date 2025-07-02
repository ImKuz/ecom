package io.kuz.ecom.shop.infra.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ShopGrpcServiceRunner {

    @Value("${grpc.port:9090}")
    private int port;

    private Server server;

    private final ShopGrpcService service;

    public ShopGrpcServiceRunner(ShopGrpcService service) {
        this.service = service;
    }

    @PostConstruct
    public void start() throws Exception {
        server = ServerBuilder.forPort(port)
            .addService(service)
            .build()
            .start();
        System.out.println("gRPC server started on port " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server...");
            ShopGrpcServiceRunner.this.stop();
            System.err.println("gRPC server shut down.");
        }));
    }

    @PreDestroy
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }
}