package com.ai.devops.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PipelineEvent {
    private UUID pipelineId;
    private String repositoryUrl;
    private String branch;
    private String status;
    private String message;
    private long timestamp;
}
