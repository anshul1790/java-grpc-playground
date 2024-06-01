package com.learn.javagrpcplayground.common;

import com.learn.javagrpcplayground.sec06.BankServiceImpl;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class GrpcServer {

    private final Server server;

    GrpcServer(Server server) {
        this.server = server;
    }

    public static GrpcServer create(BindableService... services) {
        return create(6565, services);
    }

    public static GrpcServer create(int port, BindableService... services) {
        var builder = ServerBuilder.forPort(port);
        Arrays.asList(services).forEach(builder::addService);
        return new GrpcServer(builder.build());
    }

    public GrpcServer start() {
        var services = server
                .getServices()
                .stream()
                .map(ServerServiceDefinition::getServiceDescriptor)
                .map(ServiceDescriptor::getName)
                        .toList();
        try {
            server.start();
            log.info("server started, listening on port {}. Services: {}", server.getPort(), services);
            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void await() {
        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        server.shutdownNow();
        log.info("server stopped");
    }

    /*public static void main(String[] args) throws IOException, InterruptedException {
        var server = ServerBuilder.forPort(6565)
                .addService(new BankServiceImpl())
                .build();

        server.start();

        server.awaitTermination();
    }*/
}
