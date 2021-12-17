package com.poc.kafkaTransactionManager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaTransactionManagerApplication

fun main(args: Array<String>) {
	runApplication<KafkaTransactionManagerApplication>(*args)
}
