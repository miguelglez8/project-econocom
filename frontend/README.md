# Frontend Angular - Login & SSO

Este proyecto es un **frontend Angular 16** que implementa un **login funcional y SSO simulado**, listo para integrarse con un backend Spring Boot con autenticación JWT.  

Incluye:  
- Componente de login con validación de campos.  
- Integración con el endpoint `/api/auth/login`.  
- Flujo de **Single Sign-On (SSO) simulado**.  
- Estilos basados en Material 16.2.14 y CSS personalizado.  

---

## 🔹 Requisitos

- Node.js 20.x  
- Angular CLI 16.2.16  
- Material 16.2.14  

---

## 🔹 Estructura del proyecto

src/
├─ app/
│ ├─ app-routing.module.ts           # Archivo de rutas principales de la aplicación
│ ├─ app.component.ts                # Componente raíz de la aplicación
│ ├─ app.module.ts                   # Módulo principal de Angular donde se importan todos los módulos
│ ├─ core/                           # Código central compartido (servicios y modelos)
│ │ ├─ models/                       # Modelos de datos y DTOs
│ │ │ ├─ login.request.ts            # Interfaz para la solicitud de login
│ │ │ ├─ login.response.ts           # Interfaz para la respuesta del login
│ │ │ ├─ sso.request.ts              # Interfaz para la solicitud SSO
│ │ │ └─ sso.response.ts             # Interfaz para la respuesta SSO
│ │ └─ services/                     # Servicios que contienen la lógica de negocio y comunicación HTTP
│ │   ├─ auth.service.ts             # Servicio de autenticación (login y SSO)
│ │   └─ token.service.ts            # Servicio para manejo de tokens JWT en el frontend
│ ├─ features/                        # Funcionalidades agrupadas por dominio
│ │ └─ auth/                          # Módulo de autenticación
│ │   ├─ login/                       # Componente de login tradicional
│ │   │ ├─ login.component.ts         # Lógica del componente de login
│ │   │ ├─ login.component.html       # Template HTML del login
│ │   │ └─ login.component.scss       # Estilos SCSS del login
│ │   └─ sso-callback/                # Componente de callback SSO
│ │     ├─ sso-callback.component.ts   # Lógica del callback SSO
│ │     └─ sso-callback.component.html # Template HTML del callback SSO
│ └─ shared/                           # Código compartido entre componentes
│   └─ material.module.ts             # Módulo Angular para importar Material y componentes comunes
└─ assets/                             # Recursos estáticos de la aplicación
  ├─ fonts/                            # Fuentes personalizadas
  ├─ i18n/                             # Archivos de internacionalización
  ├─ icons/                            # Íconos utilizados en la aplicación
  ├─ images/                           # Imágenes de la aplicación y diseño
  └─ scss/                             # Librerías SCSS, variables y mixins globales

---

## 🔹 Componentes principales

| Componente | Descripción |
|------------|-------------|
| `LoginComponent` | Renderiza la interfaz de login con campos de correo y contraseña, botón "ENTRAR" y botón "Iniciar sesión con SSO". Maneja validaciones básicas y muestra mensajes de error. |
| `SsoCallbackComponent` | Maneja la redirección del proveedor SSO simulado, lee parámetros de la URL (`code`) y envía la información al backend. Muestra el resultado de la autenticación SSO. |

---

## 🔹 Servicios

| Servicio | Funcionalidad |
|----------|---------------|
| `AuthService` | Encargado de comunicarse con el backend (`/api/auth/login`, `/api/auth/sso` y `/api/auth/sso/callback`). Maneja peticiones POST y GET, y controla la respuesta para mostrar mensajes de éxito o error. |

---

## 🔹 Cómo correr el proyecto

1. Clonar el repositorio y abrir en tu IDE o editor favorito.  
2. Instalar dependencias:  

```bash
npm install
```

3. Levantar el servidor de desarrollo:

```bash
ng serve
```

4. Acceder a la app en: http://localhost:4200

🔹 Login tradicional (JWT)

El usuario ingresa correo electrónico y contraseña (correo: admin@test.com, contraseña: 123a).

1. Se envía un POST a /api/auth/login.

2. El backend responde con accessToken y refreshToken.

3. En caso de error, se muestra una alerta en pantalla -> Error de autenticación: Credenciales inválidas

4. En caso de éxito, se muestra una alerta de -> Login correcto

5. También apareceran mensajes de error cuando se introducen formatos inválidos tanto de correo como contraseña

🔹 Flujo SSO (Single Sign-On) simulado

1. El usuario hace clic en "Iniciar sesión con SSO".

2. Angular realiza un GET a /api/auth/sso.

3. El backend devuelve una redirección 302 a un proveedor SSO simulado.

4. El usuario es redirigido de vuelta a /sso/callback?code=SIMULATED_CODE_123.

5. SsoCallbackComponent envía el code al endpoint /api/auth/sso/callback.

6. El backend responde con éxito o error, y el frontend muestra el resultado.

7. En este caso siempre devolverá una alerta de Autenticación SSO exitosa al pasar un código fijo en el backend, pero se ha probado todo a través de Postman (cabe destacar que si no se manda el código en el back nuestra aplicación mostrará los errores en forma de alerta también, ya sea Código de autorización inválido u otro caso)