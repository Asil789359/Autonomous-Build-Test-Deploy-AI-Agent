package com.ai.devops.build;

import com.ai.devops.common.PipelineEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class BuildManager {

    private final KafkaTemplate<String, PipelineEvent> kafkaTemplate;

    public BuildManager(KafkaTemplate<String, PipelineEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "build-requests", groupId = "build-group")
    public void handleBuildRequest(PipelineEvent event) {
        System.out.println("Received build request for: " + event.getRepositoryUrl());

        PipelineEvent result = executeBuild(event.getRepositoryUrl());
        result.setPipelineId(event.getPipelineId());
        result.setRepositoryUrl(event.getRepositoryUrl());

        if ("SUCCESS".equals(result.getStatus())) {
            kafkaTemplate.send("test-requests", result);
        } else {
            kafkaTemplate.send("pipeline-failures", result);
        }
    }

    public PipelineEvent executeBuild(String repoPath) {
        // Logic to detect Maven/Gradle
        // (Existing implementation below)
        boolean isMaven = new File(repoPath, "pom.xml").exists();
        boolean isGradle = new File(repoPath, "build.gradle").exists();

        if (isMaven) {
            return runMavenBuild(repoPath);
        } else if (isGradle) {
            return runGradleBuild(repoPath);
        } else {
            return PipelineEvent.builder()
                    .status("FAILED")
                    .message("No supported build system detected")
                    .build();
        }
    }

    private PipelineEvent runMavenBuild(String repoPath) {
        // Mocking execution for now
        return PipelineEvent.builder()
                .status("SUCCESS")
                .message("Maven build completed successfully")
                .build();
    }

    private PipelineEvent runGradleBuild(String repoPath) {
        return PipelineEvent.builder()
                .status("SUCCESS")
                .message("Gradle build completed successfully")
                .build();
    }
}
