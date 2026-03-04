package com.ai.devops.healing;

import com.ai.devops.common.PipelineEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HealingManager {

    private final KafkaTemplate<String, PipelineEvent> kafkaTemplate;

    public HealingManager(KafkaTemplate<String, PipelineEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "monitor-alerts", groupId = "healing-group")
    public void handleAlert(PipelineEvent alertEvent) {
        System.out.println(
                "CRITICAL ALERT RECEIVED: " + alertEvent.getMessage() + " for Repo: " + alertEvent.getRepositoryUrl());

        // Simulating autonomous self-healing logic
        // Step 1: Analyze alert
        // Step 2: Trigger hotfix or restart

        PipelineEvent healingAction = PipelineEvent.builder()
                .pipelineId(alertEvent.getPipelineId())
                .repositoryUrl(alertEvent.getRepositoryUrl())
                .status("HEALING_STARTED")
                .message("Autonomous healing triggered: Restarting pods and checking for hotfixes.")
                .timestamp(System.currentTimeMillis())
                .build();

        kafkaTemplate.send("pipeline-events", healingAction);

        // Mock successful healing
        healingAction.setStatus("HEALED");
        healingAction.setMessage("System recovered successfully after pod restart.");
        kafkaTemplate.send("pipeline-events", healingAction);
    }
}
