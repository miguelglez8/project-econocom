# 🧪 Technical Challenge - Full Stack (Spring Boot + Angular)

Este proyecto corresponde a una prueba técnica Full-Stack cuyo objetivo es implementar:

- 🔐 Autenticación JWT
- 🔄 Refresh Token
- 🔑 Flujo SSO simulado
- 🎨 Interfaz de Login basada en diseño Figma
- 🔗 Integración completa Frontend + Backend

---

# 🏗️ Arquitectura

El proyecto está dividido en dos aplicaciones independientes:

```
/backend   → Spring Boot (API REST + JWT + SSO simulado)
/frontend  → Angular 16 + Material (Login + SSO)
```

---

# 🛠️ Tecnologías utilizadas

## Backend
- Java 1.8
- Spring Boot 2.7.18
- Spring Web
- JWT
- Maven

## Frontend
- Angular 16.2.16
- Angular Material 16.2.14
- SCSS
- RxJS
- HttpClient

---

# 📸 Captura de pantalla

<p align="center">
  <img src="docs/images/login.png" width="700"/>
</p>

---

# 🚀 Cómo ejecutar el proyecto completo

## 1️⃣ Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Disponible en:

```
http://localhost:8080
```

---

## 2️⃣ Frontend

```bash
cd frontend
npm install
ng serve
```

Disponible en:

```
http://localhost:4200
```

⚠️ El backend debe estar corriendo antes de probar login o SSO.

---

# 🔐 Funcionalidades implementadas

## ✅ Login tradicional

- Validación de email y contraseña
- Comunicación con `/api/auth/login`
- Recepción de:
  - accessToken
  - refreshToken
- Manejo de errores

---

## 🔄 Refresh Token

- Endpoint `/api/auth/refresh`
- Generación de nuevo accessToken
- Validación de expiración

---

## 🔑 Flujo SSO Simulado

1. Usuario hace clic en "Iniciar sesión con SSO"
2. Frontend llama a `/api/auth/sso`
3. Backend responde con 302
4. Redirección a `/sso-callback`
5. Validación del `code`
6. Respuesta con token simulado

---

# 📂 Estructura general

```
backend/
frontend/
```

El frontend sigue arquitectura modular:

- core (services, models)
- features (auth)
- shared (material module)

---

# 🧪 Casos de prueba cubiertos

- Login correcto
- Login con credenciales inválidas
- Token expirado
- Refresh token válido
- SSO exitoso
- SSO con código inválido
- SSO cancelado

---

# 🎯 Criterios cubiertos

✔️ Comunicación HTTP correcta  
✔️ Manejo de CORS  
✔️ Organización modular  
✔️ Buenas prácticas Angular  
✔️ Buenas prácticas Spring Boot  
✔️ Manejo de errores  
✔️ Simulación completa de flujo SSO  

---

# ⏱️ Tiempo estimado

- Implementación base: 4–8 horas
- Implementación SSO: +2–4 horas

---

# 📌 Notas finales

Este proyecto demuestra:

- Comprensión del flujo JWT
- Manejo de refresh token
- Entendimiento del flujo SSO
- Integración Frontend–Backend
- Estructuración profesional de proyectos

---
