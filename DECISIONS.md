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

## [2026-06-23] #3 Camunda webapps + in-memory H2
**Goal:** make the running process visible and keep persistence setup-free.
**Decision:** use the Camunda 7 *webapp* starter (Cockpit/Tasklist/Admin) on an
in-memory H2 database.
**Why:** Cockpit gives a visual view of deployed processes and live instances —
valuable for demoing and debugging. H2 in-memory means zero external setup and a
clean state on every restart. Trade-off: state is not persisted across restarts;
production would use PostgreSQL.

## [2026-06-23] #4 Process modeling in BPMN 2.0 (Camunda 7) + auto-deploy from resources
**Goal:** model the booking workflow visually and have it deployed automatically.
**Decision:** author the process as a `.bpmn` (BPMN 2.0) file in Camunda Modeler,
targeting Camunda 7, and place it under `src/main/resources` so the Spring Boot
starter auto-deploys it on startup.
**Why:** BPMN 2.0 is the open standard the role calls for; keeping the diagram in
`resources` means the running app and the process model are always in sync, with
no manual deployment step.

## [2026-06-23] #5 BPMN deployment fixes: history TTL + service task placeholder
**Goal:** get the process to deploy cleanly on Camunda 7.
**Decision:** set `historyTimeToLive=180` on the process and a temporary
`expression=${true}` on the Service Task.
**Why:** Camunda 7.x enforces a non-null history TTL (for History Cleanup), and
every service task must declare an implementation (class/delegateExpression/
expression). The `${true}` is a deliberate placeholder; it will be replaced with
a real Java delegate that performs the extraction logic.