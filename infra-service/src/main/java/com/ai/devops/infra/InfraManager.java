package com.ai.devops.infra;

import com.ai.devops.common.PipelineEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InfraManager {

    private final KafkaTemplate<String, PipelineEvent> kafkaTemplate;

    public InfraManager(KafkaTemplate<String, PipelineEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "infra-requests", groupId = "infra-group")
    public void generateK8sManifests(PipelineEvent event) {
        System.out.println("Generating K8s manifests for: " + event.getRepositoryUrl());

        String k8sYaml = "apiVersion: apps/v1\n" +
                "kind: Deployment\n" +
                "metadata:\n" +
                "  name: ai-app\n" +
                "spec:\n" +
                "  replicas: 1\n" +
                "  template:\n" +
                "    spec:\n" +
                "      containers:\n" +
                "      - name: app\n" +
                "        image: ai-app-image:latest\n";

        event.setStatus("INFRA_READY");
        event.setMessage("Kubernetes manifests generated successfully");

        kafkaTemplate.send("deploy", event);
    }
}
