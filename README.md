# TrackFlow

Full-stack shipment management starter built with React, Spring Boot, MySQL, JWT, and STOMP over WebSockets.

## Run locally

Requirements: Node 20+, Java 17+, Maven, and Docker Desktop.

1. Start MySQL: `docker compose up -d db`
2. Start the API from `backend`: `mvn spring-boot:run`.
3. Start the UI from `frontend`: `npm install` then `npm run dev`.
4. Open http://localhost:5173. Register a user to begin. API runs on port 8080.

Configuration is through `backend/src/main/resources/application.yml`; use environment variables for production secrets. The development JWT secret is for local use only.

## Features

- Register and sign in with signed JWT bearer tokens; password hashes use BCrypt.
- Create, list, search, update and delete shipments.
- Dashboard metrics and shipment status distribution.
- Shipment status changes publish to `/topic/shipments`; authenticated clients can subscribe to live updates.
- MySQL persistence with schema managed by JPA in development.

## API

`POST /api/auth/register`, `POST /api/auth/login`, `GET /api/shipments`, `POST /api/shipments`, `GET /api/shipments/{id}`, `PUT /api/shipments/{id}`, `DELETE /api/shipments/{id}`, `GET /api/shipments/summary`.

WebSocket endpoint: `ws://localhost:8080/ws`; topic: `/topic/shipments`.
