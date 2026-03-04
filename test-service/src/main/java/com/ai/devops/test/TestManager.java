package com.ai.devops.test;

import com.ai.devops.common.PipelineEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestManager {

    private final KafkaTemplate<String, PipelineEvent> kafkaTemplate;

    public TestManager(KafkaTemplate<String, PipelineEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "test-requests", groupId = "test-group")
    public void handleTestRequest(PipelineEvent event) {
        System.out.println("Received test request for: " + event.getRepositoryUrl());

        // Mock execution
        event.setStatus("TESTING_COMPLETED");
        event.setMessage("All tests passed (mocked)");

        kafkaTemplate.send("deployment-requests", event);
    }
}
