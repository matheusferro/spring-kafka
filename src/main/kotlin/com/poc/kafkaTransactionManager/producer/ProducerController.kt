package com.poc.kafkaTransactionManager.producer

import com.fasterxml.jackson.databind.ObjectMapper
import com.poc.kafkaTransactionManager.SendModel
import com.poc.kafkaTransactionManager.topic.TOPICS
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
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

    private val log = LoggerFactory.getLogger(this.javaClass)

    @PostMapping("/send")
    fun send(@RequestBody sendModel: SendModel): ResponseEntity<Any> {
        for(id in 0..1000) {
            ProducerRecord(TOPICS.NAME, id, jacksonMapper.writeValueAsString(sendModel))
                .let { record ->
                    record.headers().add(TOPICS.HEADER_KEY, TOPICS.HEADER_VALUE.toByteArray())
                    kafkaTemplate.send(record)
                }
            log.info("send message with id: $id ")
        }
        log.info("sent all messages")
        return ResponseEntity.ok().build()
    }
}