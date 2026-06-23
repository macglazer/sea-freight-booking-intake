# Architecture Decision Log (lightweight ADR)

## [2026-06-23] #1 Stack & process engine
**Goal:** stand up an executable BPMN process quickly, in a single JVM.
**Decision:** Java 21 + Maven + Spring Boot 3.5 + Camunda 7 (embedded engine).
**Why:** fastest path to a working prototype with zero external infrastructure.
Conscious trade-off: Camunda 7 CE reached end of life (Oct 2025), so Camunda 8 /
Zeebe is the production-grade path. Irrelevant for a demo. Spring Boot 3.5 (not
4.x) because Camunda 7 does not support Spring Boot 4.

## [2026-06-23] #2 Packaging: executable JAR (not WAR)
**Goal:** simple, self-contained deployment.
**Decision:** fat/executable JAR with embedded Tomcat (`java -jar`).
**Why:** the standard for Spring Boot and container deployment (OpenShift/Docker,
as mentioned in the role). A WAR targets an external application server, which we
do not need here.
