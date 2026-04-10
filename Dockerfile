# ── Stage 1: build ──────────────────────────────────────────────
FROM maven:3.9.7-eclipse-temurin-21 AS builder
WORKDIR /build

# Cache dependency layer — only re-runs when pom.xml changes
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Build the fat jar (tests run in CI, skip here)
COPY src ./src
RUN mvn package -DskipTests -q

# ── Stage 2: runtime ────────────────────────────────────────────
FROM eclipse-temurin:21-jre-jammy AS runtime

# Non-root user — principle of least privilege
RUN groupadd --gid 1001 appgroup \
 && useradd  --uid 1001 --gid appgroup --no-create-home --shell /usr/sbin/nologin appuser

# curl is needed only for HEALTHCHECK; install and clean in one layer
RUN apt-get update -qq \
 && apt-get install -y --no-install-recommends curl=7.* \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=builder /build/target/stock-ms-*.jar app.jar
RUN chown appuser:appgroup app.jar

USER appuser

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=10s --start-period=45s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health/liveness || exit 1

ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "app.jar"]
