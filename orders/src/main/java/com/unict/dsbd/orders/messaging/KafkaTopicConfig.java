package com.unict.dsbd.orders.messaging;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
	
    @Value("${kafkaMainTopic}")
    private String mainTopic;
    
    @Value("${kafkaNotificationTopic}")
    private String notificationTopic;
    
    @Value("${kafkaInvoicingTopic}")
    private String invoicingTopic;
    
    @Value("${kafkaLogTopic}")
    private String logTopic;    
    
    @Bean
    public NewTopic mainTopic() {
        return TopicBuilder
        		.name(mainTopic)
        		.replicas(1)
        		.partitions(1)
        		.build();
    }
    
    @Bean
    public NewTopic notificationTopic() {
        return TopicBuilder
        		.name(notificationTopic)
        		.replicas(1)
        		.partitions(1)
        		.build();
    }
    
    @Bean
    public NewTopic invoicingTopic() {
    	return TopicBuilder.name(invoicingTopic).build();
    }
    
    @Bean
    public NewTopic loggingTopig() {
    	return TopicBuilder.name(logTopic).build();
    }
}
