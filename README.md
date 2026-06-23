# Sea Freight Booking Intake

BPMN-orchestrated sea freight booking intake with AI agents for data
extraction & validation (Java / Spring, Camunda).

## What it does

Automates the messy front end of an ocean-freight booking. A request comes in
(PDF / email / form), and the process:

1. **Receives** the booking request
2. **AI agent** extracts and validates the shipment data (ports, container type,
   HS codes, Incoterms, parties, dates)
3. **Decision gateway** — if the data is complete and consistent → confirm the
   booking; otherwise → route to a **human exception queue**
4. **Confirms** the booking and **notifies** the customer

The whole flow is modelled in BPMN 2.0 and executed by the process engine — a
small slice of the kind of process automation that powers global forwarding.

## Architecture

- **Process orchestration:** Camunda 7 (embedded), BPMN 2.0
- **Backend:** Java 21, Spring Boot 3, Maven
- **AI agents:** behind a provider-agnostic interface (Anthropic by default,
  swappable) — hexagonal architecture, so the model is a config detail
- **Reference lookups via MCP** (e.g. port / HS-code validation) so the agent
  grounds its answers instead of guessing
- **Front end:** small Angular screen for the exception queue & booking status
- **Tests:** JUnit + BPMN process tests

## How I built this with AI

Built agentically — directing AI agents, then reviewing and owning what they
produced. Key engineering decisions (what I accepted vs. rejected) are logged
in [DECISIONS.md](./DECISIONS.md).

## Running locally

> Status: work in progress.

\`\`\`bash
./mvnw spring-boot:run
\`\`\`

Set your AI provider key as an environment variable — never commit it.
