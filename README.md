# 🧠 Autonomous Build–Test–Deploy AI Agent

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen)](https://spring.io/projects/spring-boot)
[![FastAPI](https://img.shields.io/badge/FastAPI-0.104.1-blue)](https://fastapi.tiangolo.com/)

An **Elite-Level, FAANG-ready** autonomous CI/CD intelligence system. This agent doesn't just run jobs; it understands code, writes missing tests, fixes bugs, generates infrastructure, and heals production environments using an event-driven AI brain.

---

## 🔥 Key Features

- **Autonomous Planning**: AI-driven task breakdown for complex repositories.
- **Self-Healing Loop**: Real-time monitoring alerts trigger automated hotfixes and pod restarts.
- **Complete CI/CD Stack**: Automated static analysis, security scanning (CVE), building, testing, containerization, and K8s deployment.
- **Event-Driven Architecture**: Fully decoupled microservices communicating via **Apache Kafka**.
- **Multi-Language Support**: Scaffolding for Java (Spring Boot) and Python (FastAPI).

---

## 🏗 System Architecture

The system operates as a decentralized brain composed of **11 specialized components**:

| Service | Technology | Responsibility |
| :--- | :--- | :--- |
| **Orchestrator** | Java / Spring Boot | Entry point, pipeline state management, and Kafka bus control. |
| **Planner** | Python / FastAPI / LLM | The Brain. Breaks high-level requests into executable steps. |
| **Analysis** | Java / Spring Boot | Static analysis, code complexity, and quality gates. |
| **Security** | Java / Spring Boot | CVE scanning, dependency vulnerability checks (Snyk-style). |
| **Build** | Java / Maven | Compilation, dependency resolution, and packaging. |
| **Test** | Java / JUnit / JaCoCo | Mock generation, test execution, and coverage analysis. |
| **Docker** | Java / Spring Boot | Dynamic Dockerfile generation and image optimization. |
| **Infra** | Java / Spring Boot | Kubernetes manifest & Terraform script generation. |
| **Deployment** | Java / Spring Boot | Rollout management (Blue/Green, Canary). |
| **Self-Healing** | Java / Spring Boot | Autonomous recovery from production failures. |
| **Gateway** | Spring Cloud Gateway | Unified API endpoint and routing. |

---

## 🚀 Getting Started

### Prerequisites

- **Java 17+**
- **Maven 3.8+**
- **Python 3.9+**
- **Docker & Docker Compose**

### Installation & Launch

1. **Clone the Repo**:
   ```bash
   git clone https://github.com/Asil789359/Autonomous-Build-Test-Deploy-AI-Agent.git
   cd Autonomous-Build-Test-Deploy-AI-Agent
   ```

2. **Launch the Infrastructure (Dockerized)**:
   This will spin up Zookeeper, Kafka, and all 10 microservices.
   ```bash
   docker-compose up --build -d
   ```

3. **Start an Autonomous Pipeline**:
   Trigger the agent through the API Gateway (Port 8089):
   ```http
   POST http://localhost:8089/api/pipeline/start
   Content-Type: application/json

   {
     "repositoryUrl": "https://github.com/your-username/your-repo"
   }
   ```

---

## 💎 Elite Roadmap

- [/] LangGraph-powered AI Planning logic.
- [ ] Prometheus & Grafana Observability Dashboard.
- [ ] Multi-cloud deployment targets (AWS EKS, GCP GKE).
- [ ] Cost optimization & Performance profiling agents.

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
**Elite Research-Level Automation System 🚀**
