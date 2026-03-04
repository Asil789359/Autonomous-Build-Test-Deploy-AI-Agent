FROM python:3.9-slim
WORKDIR /app
COPY planner-service/requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
COPY planner-service/app ./app
ENV KAFKA_BOOTSTRAP_SERVERS=kafka:9092
CMD ["python", "app/main.py"]
