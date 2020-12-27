package com.unict.dsbd.orders.messaging;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
	
	@Value("${spring.application.name}")
	private String groupId;
	
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }
    
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
    	return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
    	kafkaListenerContainerFactory() {
    	
    	ConcurrentKafkaListenerContainerFactory<String, String>
    		factory = new ConcurrentKafkaListenerContainerFactory<>();
    	
    	factory.setConsumerFactory(consumerFactory());
    	return factory;
    }

}
