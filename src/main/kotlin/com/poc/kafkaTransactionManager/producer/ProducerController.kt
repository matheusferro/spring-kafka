package com.poc.kafkaTransactionManager.producer

import com.fasterxml.jackson.databind.ObjectMapper
import com.poc.kafkaTransactionManager.SendModel
import com.poc.kafkaTransactionManager.topic.TOPICS
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProducerController(
    private val kafkaTemplate: KafkaTemplate<Int, String>,
    private val jacksonMapper: ObjectMapper
) {

    @PostMapping("/send")
    fun send(@RequestBody sendModel: SendModel): ResponseEntity<Any> {
        ProducerRecord(TOPICS.NAME, sendModel.id, jacksonMapper.writeValueAsString(sendModel))
            .let { record ->
                record.headers().add("Header-example", "HeaderValue".toByteArray())
                kafkaTemplate.send(record)
            }
        return ResponseEntity.ok().build()
    }
}