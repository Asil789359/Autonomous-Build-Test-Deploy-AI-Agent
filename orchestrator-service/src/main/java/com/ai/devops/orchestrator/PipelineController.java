package com.ai.devops.orchestrator;

import com.ai.devops.common.PipelineEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/pipeline")
public class PipelineController {

    private final KafkaTemplate<String, PipelineEvent> kafkaTemplate;
    private final Map<UUID, PipelineEvent> pipelineStatusMap = new ConcurrentHashMap<>();

    public PipelineController(KafkaTemplate<String, PipelineEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/start")
    public PipelineEvent startPipeline(@RequestBody PipelineRequest request) {
        PipelineEvent event = PipelineEvent.builder()
                .pipelineId(UUID.randomUUID())
                .repositoryUrl(request.getRepositoryUrl())
                .status("STARTED")
                .timestamp(System.currentTimeMillis())
                .build();

        pipelineStatusMap.put(event.getPipelineId(), event);
        kafkaTemplate.send("pipeline-events", event);
        return event;
    }

    @GetMapping("/{id}/status")
    public PipelineEvent getStatus(@PathVariable UUID id) {
        return pipelineStatusMap.getOrDefault(id, PipelineEvent.builder().status("NOT_FOUND").build());
    }

    @KafkaListener(topics = { "pipeline-events", "build-requests", "test-requests", "deployment-requests",
            "infra-requests", "deploy", "pipeline-failures" }, groupId = "orchestrator-status-group")
    public void trackStatus(PipelineEvent event) {
        System.out.println(
                "Global Status Trace: Pipeline ID " + event.getPipelineId() + " -> Status: " + event.getStatus());
        pipelineStatusMap.put(event.getPipelineId(), event);
    }
}

class PipelineRequest {
    private String repositoryUrl;

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }
}
