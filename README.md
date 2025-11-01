# legendme-missions-svc

Microservicio encargado de la gestión de misiones dentro de la plataforma **LegendMe**.  
Permite al usuario crear, consultar, iniciar, pausar, completar y cancelar misiones, además de realizar búsquedas filtradas.

Este servicio sigue **Arquitectura Hexagonal (Ports & Adapters)** asegurando bajo acoplamiento, alta mantenibilidad y facilidad de pruebas.

---

## 🧱 Arquitectura


- **`application.port.in`** → casos de uso (interfaces)
- **`application.port.out`** → puertos hacia persistencia
- **`domain.model`** → entidades del dominio
- **`adapters.in.rest`** → controladores REST
- **`adapters.out.persistence`** → adapters + repositorios JPA

---

## 🧾 Variables de Entorno

| Variable | Descripción |
|----------|------------|
| `DB_URL` | URL de conexión a la base de datos |
| `DB_USERNAME` | Usuario |
| `DB_PASSWORD` | Contraseña |
| `JWT_SECRET` | Llave para validar el token |

---

## 📌 Endpoints Principales

| Acción | Método | Endpoint |
|---------|---------|---------|
| Crear misión | `POST` | `/missions` |
| Buscar misiones | `GET` | `/missions` |
| Obtener detalle | `GET` | `/missions/{missionId}` |
| Iniciar misión | `POST` | `/missions/{missionId}/start` |
| Pausar misión | `POST` | `/missions/{missionId}/pause` |
| Completar misión | `POST` | `/missions/{missionId}/complete` |
| Cancelar misión | `POST` | `/missions/{missionId}/cancel` |

> **Nota:** El `userId` se obtiene del **token JWT**, no del body.

---

## 📌 Ejemplos de Requests

### ✅ Crear misión
```http
POST /missions
{
  "categoryCode": "HEALTH",
  "title": "Correr 5km",
  "description": "Completar distancia en la mañana",
  "baseXp": 150,
  "difficulty": "MEDIUM",
  "streakGroup": "daily_run"
}
```
### ✅ Iniciar misión
```http
POST /missions/{missionId}/start
```

### ✅ Completar misión (con idempotency key)
```http

POST /missions/{missionId}/complete
{
  "idempotencyKey": "c4a1a8e2-b9fc-4b10-a209-f12b912c4567",
  "note": "Finalizada sin problemas"
}
```
---
### 🔁 Idempotency Key (Resumen)
Se usa para evitar que una misión se complete dos veces por error.

La genera el cliente/frontend

El backend la almacena y la valida

Si el cliente reintenta, obtiene la misma respuesta, no un doble completion

---

### 🧪 Testing (Postman)
Para probar:

Obtener token JWT

Llamar a los endpoints enviando el token en el header:

Authorization: Bearer <token>

---
### ✅ Estados de Misión
```http
Estado	Descripción
PENDING	Creada, aún no iniciada
IN_PROGRESS	El usuario la está realizando
PAUSED	En pausa
COMPLETED	Finalizada exitosamente
CANCELLED	Cancelada por el usuario
```
---
### 📌 Regla Importante
Siempre se valida que la misión pertenece al userId del token.
Si no coincide → 403 Forbidden.

