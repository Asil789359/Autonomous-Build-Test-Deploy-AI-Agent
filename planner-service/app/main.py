from fastapi import FastAPI
from pydantic import BaseModel
import uuid
import asyncio
import json
from aiokafka import AIOKafkaConsumer, AIOKafkaProducer

app = FastAPI(title="AI Planner Service")

class PlanRequest(BaseModel):
    repository_url: str
    task: str

@app.post("/plan")
async def create_plan(request: PlanRequest):
    return {
        "plan_id": str(uuid.uuid4()),
        "steps": [
            "Clone repository",
            "Detect tech stack",
            "Identify build system",
            "Generate build execution plan"
        ],
        "status": "PLANNING"
    }

@app.on_event("startup")
async def startup_event():
    loop = asyncio.get_event_loop()
    import os
    kafka_servers = os.getenv("KAFKA_BOOTSTRAP_SERVERS", "localhost:9092")
    app.state.kafka_consumer = AIOKafkaConsumer(
        "pipeline-events",
        loop=loop, bootstrap_servers=kafka_servers,
        group_id="planner-group",
        value_deserializer=lambda v: json.loads(v.decode('utf-8'))
    )
    app.state.kafka_producer = AIOKafkaProducer(
        loop=loop, bootstrap_servers=kafka_servers,
        value_serializer=lambda v: json.dumps(v).encode('utf-8')
    )
    await app.state.kafka_consumer.start()
    await app.state.kafka_producer.start()
    asyncio.create_task(consume_kafka())

@app.on_event("shutdown")
async def shutdown_event():
    await app.state.kafka_consumer.stop()
    await app.state.kafka_producer.stop()

async def consume_kafka():
    async for msg in app.state.kafka_consumer:
        event = msg.value
        print(f"Received event: {event}")
        # Logic to create plan and send to build-requests
        plan_event = {
            "pipelineId": event["pipelineId"],
            "repositoryUrl": event["repositoryUrl"],
            "status": "PLAN_READY",
            "message": "AI generated build plan successfully"
        }
        await app.state.kafka_producer.send_and_wait("analysis-requests", plan_event)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8001)
