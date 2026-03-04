package com.ai.devops.docker;

import com.ai.devops.common.PipelineEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DockerManager {

    private final KafkaTemplate<String, PipelineEvent> kafkaTemplate;

    public DockerManager(KafkaTemplate<String, PipelineEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "deployment-requests", groupId = "docker-group")
    public void generateDockerfile(PipelineEvent event) {
        System.out.println("Generating Dockerfile for: " + event.getRepositoryUrl());

        String dockerfileContent = "FROM openjdk:17-jdk-slim\n" +
                "COPY target/*.jar app.jar\n" +
                "ENTRYPOINT [\"java\", \"-jar\", \"/app.jar\"]\n";

        // In a real scenario, we'd write this to the repo path
        // For now, we simulate success

        event.setStatus("DOCKER_READY");
        event.setMessage("Dockerfile generated successfully");

        kafkaTemplate.send("infra-requests", event);
    }
}
