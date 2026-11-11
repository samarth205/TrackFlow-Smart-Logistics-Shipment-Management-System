# TrackFlow

Full-stack shipment management starter built with React, Spring Boot, MySQL, JWT, and STOMP over WebSockets.

**Live demo:** https://trackflow-geec.onrender.com

## Run locally

Requirements: Node 20+, Java 17+, Maven, and Docker Desktop.

1. Start MySQL: `docker compose up -d db`
2. Start the API from `backend`: `mvn spring-boot:run`.
3. Start the UI from `frontend`: `npm install` then `npm run dev`.
4. Open http://localhost:5173. Register a user to begin. API runs on port 8080.

Configuration is through `backend/src/main/resources/application.yml`; use environment variables for production secrets. The development JWT secret is for local use only.

## Deploy on Render

This repo includes a Render Blueprint. In Render, choose **New → Blueprint**, connect this repository, and apply the `trackflow` service. Render builds the React app into the Spring Boot container and exposes the dashboard, REST API, and WebSocket on one URL. The health check is `/health`.

The hosted demo uses an in-memory H2 database so it can deploy as one free web service without a separate database account. Demo accounts and shipments reset when Render restarts or sleeps the service. Local development continues to use MySQL through Docker Compose. For persistent production data, configure `DB_URL`, `DB_USER`, and `DB_PASSWORD` to point to a managed MySQL database, and set `JWT_SECRET` to a private random secret.

Render's free web services can sleep when idle and take a short time to wake. Review current hosting plans before changing the service to a paid plan.

## Features

- Register and sign in with signed JWT bearer tokens; password hashes use BCrypt.
- Create, list, search, update and delete shipments.
- Dashboard metrics and shipment status distribution.
- Shipment status changes publish to `/topic/shipments`; authenticated clients can subscribe to live updates.
- MySQL persistence with schema managed by JPA in development.

## API

`POST /api/auth/register`, `POST /api/auth/login`, `GET /api/shipments`, `POST /api/shipments`, `GET /api/shipments/{id}`, `PUT /api/shipments/{id}`, `DELETE /api/shipments/{id}`, `GET /api/shipments/summary`.

WebSocket endpoint: `ws://localhost:8080/ws`; topic: `/topic/shipments`.
