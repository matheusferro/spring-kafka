package com.poc.kafkaTransactionManager.listener

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.common.TopicPartition
import org.slf4j.LoggerFactory

class RebalanceInterceptor : ConsumerRebalanceListener {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun onPartitionsRevoked(p0: MutableCollection<TopicPartition>?) {
        log.info("[RebalanceInterceptor] onPartitionsRevoked HAS INVOKED")
    }

    override fun onPartitionsAssigned(p0: MutableCollection<TopicPartition>?) {
        log.info("[RebalanceInterceptor] onPartitionsAssigned HAS INVOKED")
    }
}