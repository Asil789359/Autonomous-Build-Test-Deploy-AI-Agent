package com.ai.devops.analysis;

import com.ai.devops.common.PipelineEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AnalysisManager {

    private final KafkaTemplate<String, PipelineEvent> kafkaTemplate;

    public AnalysisManager(KafkaTemplate<String, PipelineEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "analysis-requests", groupId = "analysis-group")
    public void performAnalysis(PipelineEvent event) {
        System.out.println("Performing static analysis for: " + event.getRepositoryUrl());

        // Mock analysis logic (detecting complexity, smells, etc.)

        event.setStatus("ANALYSIS_COMPLETED");
        event.setMessage("Static analysis passed: Complexity 12, Coverage 85%");

        kafkaTemplate.send("security-requests", event);
    }
}
