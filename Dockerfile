FROM openjdk:21-jdk-slim as base
WORKDIR /app
RUN apt-get update && apt-get install -y curl unzip zip \
    && curl -s https://get.sdkman.io | bash \
    && /bin/bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install gradle"
COPY . /app
RUN chmod +x ./gradlew
RUN ./gradlew clean build --no-daemon
FROM openjdk:21-jdk-slim as stage-1
RUN groupadd --system appgroup && useradd --system --gid appgroup appuser
WORKDIR /app
COPY --from=base /app/build/libs/DARAM-server-V2-0.0.1-SNAPSHOT.jar /app/app.jar
RUN rm -rf /app/.gradle /app/build/tmp /root/.gradle
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]