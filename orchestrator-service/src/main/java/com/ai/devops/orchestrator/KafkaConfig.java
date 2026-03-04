package com.ai.devops.orchestrator;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic pipelineEventsTopic() {
        return TopicBuilder.name("pipeline-events")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic buildRequestsTopic() {
        return TopicBuilder.name("build-requests")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
