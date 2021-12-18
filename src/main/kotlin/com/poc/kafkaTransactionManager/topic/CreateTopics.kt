package com.poc.kafkaTransactionManager.topic

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CreateTopics {

    @Bean
    fun topic(): NewTopic = NewTopic(TOPICS.NAME, 3, 1)
}


object TOPICS {
    const val NAME = "test-topic"
    const val HEADER_KEY = "headerKey"
    const val HEADER_VALUE = "headerValue"
}