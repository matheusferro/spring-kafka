package com.poc.kafkaTransactionManager.listener

import org.apache.kafka.clients.consumer.ConsumerInterceptor
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.header.Header
import org.slf4j.LoggerFactory

class ListenerInterceptor : ConsumerInterceptor<Any, Any> {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun onConsume(records: ConsumerRecords<Any, Any>?): ConsumerRecords<Any, Any> {
        records?.partitions()?.forEach { topicPartition ->
            records.records(topicPartition).forEach { consumerRecord ->
                val myHeaders: MutableList<Header> = mutableListOf()
                consumerRecord.headers().headers("Header1").forEach(myHeaders::add)
                log.info("HEADERS FOUND: $myHeaders")
            }
        }
        return records!!
    }

    override fun configure(configs: MutableMap<String, *>?) {}
    override fun close() {}
    override fun onCommit(offsets: MutableMap<TopicPartition, OffsetAndMetadata>?) = log.info("COMMIT HAPPENED")
}