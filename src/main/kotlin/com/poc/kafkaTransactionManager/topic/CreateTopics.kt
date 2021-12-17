package com.poc.kafkaTransactionManager.topic

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CreateTopics {

    @Bean
    fun topic(): NewTopic = NewTopic(TOPICS.NAME, 1, 1)
}


object TOPICS {
    const val NAME = "test-topic"
}