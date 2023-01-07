package com.example.springgrpc

import com.example.grpc.MyServiceProto
import com.example.grpc.TextServiceGrpc
import io.grpc.stub.StreamObserver
import com.google.gson.Gson

class TextServiceImpl : TextServiceGrpc.TextServiceImplBase() {

    private val consumer = Consumer
    private val producer = Producer
    private val gson = Gson()

    override fun sendText(
        request: MyServiceProto.TextMessage,
        responseObserver: StreamObserver<MyServiceProto.OKResponse>
    ) {
        val message = Message(request.text)
        val jsonMessage = gson.toJson(message)
        producer.sendMessage(jsonMessage)
        val response = MyServiceProto.OKResponse.newBuilder().setText("Message sent")
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun getText(
        request: MyServiceProto.GetTextRequest,
        responseObserver: StreamObserver<MyServiceProto.AllMessages>
    ) {
        val consumerRecords = consumer.getMessages()
        //val jsonList = ArrayList<Message>()
        val messages = ArrayList<String>()
        for (record in consumerRecords!!) {
            //jsonList.add(gson.fromJson(record.value(), Message().javaClass))
            messages.add(record.value())
        }
        val response = MyServiceProto.AllMessages.newBuilder().addAllMessage(messages).build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}