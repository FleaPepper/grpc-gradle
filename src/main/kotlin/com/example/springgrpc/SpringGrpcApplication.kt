package com.example.springgrpc

import io.grpc.ServerBuilder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringGrpcApplication

fun main(args: Array<String>) {
	//runApplication<SpringGrpcApplication>(*args)

	val server = ServerBuilder.forPort(8080).addService(TextServiceImpl()).build()

	server.start()
	println("Server started")

	server.awaitTermination()
}
