# Backend Spring Boot - JWT Authentication

Este proyecto es un **backend Spring Boot** que implementa autenticación con **JWT**, con endpoints protegidos y públicos, listo para integrarse con un frontend (Angular, React, etc.).

---

## 🔹 Requisitos

- Java 1.8
- Maven 3.x
- Spring Boot 2.7.18

---

## 🔹 Endpoints disponibles

| Endpoint | Método | Descripción | Protegido |
|----------|--------|-------------|-----------|
| `/api/auth/login` | POST | Login del usuario, devuelve `accessToken` y `refreshToken` | ❌ No requiere token |
| `/api/auth/refresh` | POST | Refresca el `accessToken` usando `refreshToken` | ❌ No requiere token |
| `/api/test` | GET | Endpoint de prueba para validar JWT | ✅ Requiere token válido |

---

## 🔹 Cómo correr el proyecto

1. Clonar el repositorio y abrir en tu IDE favorito.
2. Instalar dependencias con Maven:

```bash
mvn clean install
```

---

## 🔹 Casos de prueba
| Caso | Header Authorization                         | Body | Resultado esperado                       | Código HTTP                                  |
| ---- | -------------------------------------------- | ---- | ---------------------------------------- |----------------------------------------------|
| 1    | No enviado                                   | N/A  | Bloqueado, token faltante                | 403 Forbidden                             |
| 2    | Token inválido (ej: `"Bearer abc123"`)       | N/A  | Bloqueado, token inválido                | 401 Unauthorized                             |
| 3    | Token expirado                               | N/A  | Bloqueado, token expirado                | 401 Unauthorized                             |
| 4    | Token válido                                 | N/A  | Acceso permitido                         | 200 OK → `"Token válido, acceso permitido!"` |
| 5    | Header mal formado (`"abc123"` sin `Bearer`) | N/A  | Bloqueado, token inválido                | 401 Unauthorized                             |

---

## 🔹 Autenticación con JWT y Refresh Token

Este backend utiliza **JWT (JSON Web Tokens)** para proteger los endpoints y permitir acceso seguro desde el frontend.

### 1️⃣ Login (`/api/auth/login`)

Al iniciar sesión, el backend devuelve **dos tokens**:

| Token | Uso | Expiración típica |
|-------|-----|-----------------|
| **Access Token** | Se envía en el header `Authorization: Bearer <token>` para acceder a endpoints protegidos | Corto (ej: 5-15 minutos) |
| **Refresh Token** | Se envía solo al endpoint `/api/auth/refresh` para obtener un nuevo access token cuando el actual expire | Largo (ej: 1 día) |

**Ejemplo de request:**

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@test.com",
  "password": "123a"
}
```

**Ejemplo de response (caso correcto):**

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...token",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...token",
   "error": null
}
```

**Ejemplo de response (credenciales inválidas):**

```json
{
   "accessToken": null,
   "refreshToken": null,
   "errorMessage": "Credenciales inválidas"
}
```

---

### 2️⃣ Refrescar token (`/api/auth/refresh`)

Al usar el **refresh token**, el backend devuelve un **nuevo access token**:

| Token | Uso | Expiración típica |
|-------|-----|-----------------|
| **Nuevo Access Token** | Se envía en el header `Authorization: Bearer <token>` para seguir accediendo a endpoints protegidos | Corto (ej: 5-15 minutos) |
| **Refresh Token** | El mismo refresh token original sigue válido hasta su expiración; se sigue usando para futuros refreshes | Largo (ej: 1 día) |

- El cliente **envía el refresh token** al endpoint `/api/auth/refresh`.
- El backend valida que el refresh token sea **válido y no expirado**.
- Si es correcto, se genera un **nuevo access token** y se devuelve en la respuesta.
- Este nuevo access token reemplaza al anterior en el cliente para continuar haciendo peticiones a endpoints protegidos.

**Ejemplo de request:**

```http
POST /api/auth/refresh
Content-Type: application/json

{
  "refreshToken": "<refreshToken_obtenido_del_login>"
}
```

**Ejemplo de response:**

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...nuevo"
}
```

---

**Flujo típico:**

1. El usuario hace login con su email y contraseña.
2. El backend valida las credenciales.
3. Se generan **accessToken** y **refreshToken**.
4. El cliente guarda ambos:
    - accessToken → para peticiones a endpoints protegidos (`/api/test`, etc.)
    - refreshToken → para renovar el accessToken cuando expire.

---

# 🔹 EXTRA – Flujo SSO (Single Sign-On) Simulado

Además del login tradicional con JWT, el backend implementa un **flujo SSO simulado**, representando cómo funcionaría una autenticación con un proveedor externo (Google, Azure AD, etc.).

---

## 🔹 Endpoints SSO

| Endpoint | Método | Descripción | Protegido |
|----------|--------|-------------|-----------|
| `/api/auth/sso` | GET | Inicia el flujo SSO y redirige al proveedor simulado | ❌ No requiere token |
| `/api/auth/sso/callback` | GET | Recibe el código de autorización y valida el flujo SSO | ❌ No requiere token |

---

## 🔹 Flujo SSO Simulado

1. El cliente llama a `/api/auth/sso`.
2. El backend responde con una **redirección HTTP 302** hacia un proveedor SSO simulado.
3. El proveedor simulado redirige de vuelta a: /api/auth/sso/callback?code=SIMULATED_CODE_123

4. El backend valida el código:
- Si es correcto → devuelve un token simulado.
- Si es incorrecto → devuelve error.

---

## 🔹 Casos de prueba – SSO

| Caso | Endpoint | Parámetros | Resultado esperado | Código HTTP |
|------|----------|------------|--------------------|------------|
| 1 | `/api/auth/sso` | N/A | Redirección al proveedor SSO simulado | 302 Found |
| 2 | `/api/auth/sso/callback` | `code=SIMULATED_CODE_123` | Autenticación SSO exitosa + token | 200 OK |
| 3 | `/api/auth/sso/callback` | `code=INVALID` | Código de autorización inválido | 400 Bad Request |
| 4 | `/api/auth/sso/callback` | `error=access_denied` | Autenticación SSO fallida | 400 Bad Request |
| 5 | `/api/auth/sso/callback` | Sin parámetros | No se recibió código de autorización | 400 Bad Request |

---

---

## 🔹 Pruebas SSO Simulado

```http
GET /api/auth/sso
```

Esperado:
- 302 Found
- Header `Location` con la URL del proveedor SSO simulado.

Ejemplo: http://simulated-sso-provider.com/auth?client_id=simulated-client-id&redirect_uri=http://localhost:8080/api/auth/sso/callback&code=SIMULATED_CODE_123

```http
GET /api/auth/sso/callback?code=SIMULATED_CODE_123
```

Esperado: 200 OK

```json
{
  "success": true,
  "message": "Autenticación SSO exitosa",
  "accessToken": "SIMULATED_JWT_TOKEN"
}
```

```http
GET /api/auth/sso/callback?code=INVALID
```

Esperado: 400 Bad Request

```json
{
  "success": false,
  "message": "Código de autorización inválido",
  "accessToken": null
}
```

```http
GET /api/auth/sso/callback?error=access_denied
```

Esperado: 400 Bad Request

```json
{
  "success": false,
  "message": "Autenticación SSO fallida: access_denied",
  "accessToken": null
}
```

```http
GET /api/auth/sso/callback
```

Esperado: 400 Bad Request

```json
{
  "success": false,
  "message": "No se recibió código de autorización",
  "accessToken": null
}
```