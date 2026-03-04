package com.ai.devops.security;

import com.ai.devops.common.PipelineEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SecurityManager {

    private final KafkaTemplate<String, PipelineEvent> kafkaTemplate;

    public SecurityManager(KafkaTemplate<String, PipelineEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "security-requests", groupId = "security-group")
    public void scanSecurity(PipelineEvent event) {
        System.out.println("Scanning for security vulnerabilities in: " + event.getRepositoryUrl());

        // Mock CVE scan (Snyk/OWASP style)

        event.setStatus("SECURITY_CLEAN");
        event.setMessage("Security scan passed: 0 critical vulnerabilities found.");

        kafkaTemplate.send("build-requests", event); // Forwarding back to build or next logical step
    }
}
