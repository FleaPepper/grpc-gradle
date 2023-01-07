package com.example.springgrpc

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Tests : TestBase() {

    private var consumer = Consumer
    private val producer = Producer

    @BeforeEach
    fun init() {
        //Assumptions.assumeTrue(TextServiceImpl.isRunning)
        //var records = consumer.getMessages()
        //consumer.getMessages()
        consumer.getMessages()
    }

    @AfterAll
    fun after() {
        consumer.getMessages()
    }

    @Test
    fun contextLoads() {
        assertTrue(true)
    }

    @Test
    fun messageIsDelivered() {
        producer.sendMessage("test message!")
        val records = consumer.getMessages()
        assertTrue(records?.last()!!.value().equals("test message!"))
    }

    @Test
    fun messagesSaveOrder() {
        producer.sendMessage("first message")
        //producer.flush()
        producer.sendMessage("second message")
        //producer.flush()
        producer.sendMessage("third message")
        //producer.flush()
        val records = consumer.getMessages()
        for (record in records!!)
            println(record.value())
        println(records?.count())
        assertTrue(records?.elementAt(0)!!.value().equals("first message"))
        assertTrue(records?.elementAt(1)!!.value().equals("second message"))
        assertTrue(records?.elementAt(2)!!.value().equals("third message"))
    }
}