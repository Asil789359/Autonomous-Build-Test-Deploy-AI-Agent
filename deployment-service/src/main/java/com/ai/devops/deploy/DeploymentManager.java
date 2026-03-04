package com.ai.devops.deploy;

import com.ai.devops.common.PipelineEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DeploymentManager {

    @KafkaListener(topics = "deploy", groupId = "deploy-group")
    public void executeDeployment(PipelineEvent event) {
        System.out.println("Executing deployment for: " + event.getRepositoryUrl());

        // Mock deployment execution (kubectl apply -f ...)

        System.out.println("PIPELINE COMPLETED SUCCESSFULLY for ID: " + event.getPipelineId());
    }
}
