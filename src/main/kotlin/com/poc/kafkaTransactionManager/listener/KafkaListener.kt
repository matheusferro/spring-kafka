package com.poc.kafkaTransactionManager.listener

import com.fasterxml.jackson.databind.ObjectMapper
import com.poc.kafkaTransactionManager.SendModel
import com.poc.kafkaTransactionManager.topic.TOPICS
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class KafkaListener(
    private val jacksonMapper: ObjectMapper,
    private val concurrentKafkaListenerContainerFactory: ConcurrentKafkaListenerContainerFactory<String, String>
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @KafkaListener(
        topics = [TOPICS.NAME]
//        ,properties = [
//            "max.poll.interval.ms=6000"
//        ]
    )
    fun getMessages(@Payload consumerRecord: ConsumerRecord<String, String>, ack: Acknowledgment) {
        log.info("key: ${consumerRecord.key()}")
        log.info("body: ${consumerRecord.value()}")
        log.info(jacksonMapper.readValue(consumerRecord.value(), SendModel::class.java).toString())
        Thread.sleep(4000)
        ack.acknowledge()
    }

    @PostConstruct
    fun ini() {
        concurrentKafkaListenerContainerFactory.containerProperties
            .setConsumerRebalanceListener(consumerRebalanceListener())
    }

    @Bean
    fun consumerRebalanceListener() = object : ConsumerRebalanceListener {
        override fun onPartitionsRevoked(partitions: MutableCollection<TopicPartition>?) {
            log.info("[RebalanceInterceptor] onPartitionsRevoked HAS INVOKED")
        }

        override fun onPartitionsAssigned(partitions: MutableCollection<TopicPartition>?) {
            log.info("[RebalanceInterceptor] onPartitionsAssigned HAS INVOKED")
        }
    }
}