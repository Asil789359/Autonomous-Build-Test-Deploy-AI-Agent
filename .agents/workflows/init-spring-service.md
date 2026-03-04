---
description: How to initialize a Spring Boot service in the AI DevOps system
---

1. Navigate to the service directory: `cd ai-devops-system/<service-name>`
2. Run the Spring Initializr CLI or Maven archetype:
   `mvn archetype:generate -DgroupId=com.ai.devops -DartifactId=<service-name> -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false`
3. Update `pom.xml` with Spring Boot parent and dependencies.
4. Add the main application class.
5. Create standard package structure: `com.ai.devops.<service>.{config, controller, service, model, repository}`.
