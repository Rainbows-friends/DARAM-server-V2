FROM openjdk:21-jdk-slim AS base
RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    && curl -s https://get.sdkman.io | bash \
    && /bin/bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install gradle" \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*
RUN groupadd --system appgroup && useradd --system --gid appgroup appuser
WORKDIR /app
COPY . /app
RUN ./gradlew clean build --no-daemon
FROM openjdk:21-jre-slim
RUN groupadd --system appgroup && useradd --system --gid appgroup appuser
USER appuser
WORKDIR /app
COPY --from=base /app/build/libs/your-app-name.jar /app/app.jar
RUN rm -rf /app/.gradle /app/build/tmp /root/.gradle
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
EXPOSE 8080