package com.poc.kafkaTransactionManager.listener

import com.fasterxml.jackson.databind.ObjectMapper
import com.poc.kafkaTransactionManager.SendModel
import com.poc.kafkaTransactionManager.topic.TOPICS
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class KafkaListener(
    private val jacksonMapper: ObjectMapper
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @KafkaListener(
        topics = [TOPICS.NAME]
    )
    fun getMessages(@Payload consumerRecord: ConsumerRecord<String, String>, ack: Acknowledgment) {
        log.info(consumerRecord.toString())
        log.info(jacksonMapper.readValue(consumerRecord.value(), SendModel::class.java).toString())
        ack.acknowledge()
    }
}