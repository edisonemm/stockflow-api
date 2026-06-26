# StockFlow API - Phase 2

> API REST profesional para gestión de inventario. Construida con Spring Boot, JPA y H2 Database.

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0%2B-green)
![Maven](https://img.shields.io/badge/Maven-3.9%2B-blue)
![H2 Database](https://img.shields.io/badge/H2%20Database-2.0%2B-red)

## 📋 Descripción

**StockFlow API** es una API REST completa para gestión de inventario. Construida con Spring Boot, JPA/Hibernate y H2 Database, proporciona endpoints profesionales para:

- ✅ Crear, leer, actualizar y eliminar productos
- ✅ Búsqueda flexible por código o nombre
- ✅ Alertas automáticas de stock bajo
- ✅ Reportes de inventario en tiempo real
- ✅ Cálculo de valor total del inventario
- ✅ Persistencia en base de datos

## 🚀 Características

| Feature | Descripción |
|---|---|
| **REST API Completa** | 7 endpoints implementados y documentados |
| **Base de Datos** | H2 Database integrada (sin configuración externa) |
| **CORS Habilitado** | Acepta requests desde cualquier origen |
| **Manejo de Errores** | Respuestas HTTP claras con códigos de estado |
| **Validación** | Todas las entradas se validan antes de guardar |
| **JPA/Hibernate** | ORM que mapea objetos a tablas automáticamente |

## 🏗️ Arquitectura

StockFlow API sigue el patrón **MVC con capas**:

```
HTTP Request
    ↓
ProductoController (Capa de Presentación)
    ↓
InventarioService (Capa de Lógica de Negocio)
    ↓
ProductoRepository (Capa de Datos)
    ↓
H2 Database
```

### Componentes

- **`Producto.java`** — Entidad JPA mapeada a tabla `produtos`
- **`ProductoRepository.java`** — Interfaz JPA que Spring implementa automáticamente
- **`InventarioService.java`** — Lógica de negocio inyectada en el controller
- **`ProductoController.java`** — Endpoints REST (`@RestController`)
- **`application.yml`** — Configuración de Spring Boot y base de datos

## 📦 Requisitos

- **Java 17** o superior
- **Maven 3.9** o superior
- **Postman** (para probar endpoints, opcional pero recomendado)

## 🔧 Instalación

### Clonar el repositorio

```bash
git clone https://github.com/tuusuario/stockflow-api.git
cd stockflow-api
```

### Descargar dependencias

Maven descargará automáticamente todas las dependencias al ejecutar:

```bash
mvn clean install
```

## ▶️ Cómo Ejecutar

### Opción 1: Desde IntelliJ IDEA

1. Abre el proyecto en IntelliJ
2. Abre la clase `StockflowApiApplication.java`
3. Haz clic en el botón **Run** (▶️)
4. Verás: `Tomcat started on port(s): 8080`

### Opción 2: Desde Terminal

```bash
mvn spring-boot:run
```

### Opción 3: Compilar y ejecutar JAR

```bash
mvn clean package
java -jar target/stockflow-api-0.0.1-SNAPSHOT.jar
```

La API estará disponible en: **`http://localhost:8080`**

## 📡 Endpoints

### Base URL
```
http://localhost:8080/api/products
```

### 1. Crear Producto

```http
POST /api/products
Content-Type: application/json

{
  "name": "Coca-Cola",
  "code": "COCA001",
  "quantity": 50,
  "price": 2.50,
  "minimumStock": 10
}
```

**Respuesta (201 Created):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Coca-Cola",
  "code": "COCA001",
  "quantity": 50,
  "price": 2.50,
  "minimumStock": 10
}
```

---

### 2. Obtener Todos los Productos

```http
GET /api/products
```

**Respuesta (200 OK):**
```json
[
  {
    "id": "550e8400...",
    "name": "Coca-Cola",
    "code": "COCA001",
    "quantity": 50,
    "price": 2.50,
    "minimumStock": 10
  },
  { ... más productos }
]
```

---

### 3. Buscar por Código

```http
GET /api/products/code/{code}
```

**Ejemplo:**
```http
GET /api/products/code/COCA001
```

**Respuesta (200 OK):**
```json
{
  "id": "550e8400...",
  "name": "Coca-Cola",
  "code": "COCA001",
  "quantity": 50,
  "price": 2.50,
  "minimumStock": 10
}
```

**Si no existe (404 Not Found):**
```json
"❌ Product not found"
```

---

### 4. Buscar por Nombre

```http
GET /api/products/search?name={nombre}
```

**Ejemplo:**
```http
GET /api/products/search?name=Coca
```

**Respuesta (200 OK):**
```json
[
  {
    "id": "550e8400...",
    "name": "Coca-Cola",
    "code": "COCA001",
    ...
  }
]
```

---

### 5. Actualizar Stock

```http
PUT /api/products/{code}?quantity={nueva_cantidad}
```

**Ejemplo:**
```http
PUT /api/products/COCA001?quantity=30
```

**Respuesta (200 OK):**
```json
{
  "id": "550e8400...",
  "name": "Coca-Cola",
  "code": "COCA001",
  "quantity": 30,
  "price": 2.50,
  "minimumStock": 10
}
```

---

### 6. Eliminar Producto

```http
DELETE /api/products/{code}
```

**Ejemplo:**
```http
DELETE /api/products/COCA001
```

**Respuesta (200 OK):**
```json
"✅ Product deleted"
```

**Si no existe (404 Not Found):**
```json
"❌ Product not found"
```

---

### 7. Obtener Productos con Stock Bajo

```http
GET /api/products/alerts/low-stock
```

**Respuesta (200 OK):**
```json
[
  {
    "id": "550e8400...",
    "name": "Sprite",
    "code": "SPRITE001",
    "quantity": 5,
    "price": 2.50,
    "minimumStock": 15
  }
]
```

---

### 8. Obtener Valor Total del Inventario

```http
GET /api/products/report/total-value
```

**Respuesta (200 OK):**
```json
"Total Inventory Value: $1500.00"
```

---

### 9. Generar Reporte Completo

```http
GET /api/products/report
```

**Respuesta (200 OK):**
```
========== INVENTORY REPORT ==========
Total Products: 5
Total Inventory Value: $1500.00
Products with Low Stock: 2

[550e8400] Coca-Cola (Code: COCA001) | Stock: 50 | Price: $2.50 | Total: $125.00
[e29b41d4] Sprite (Code: SPRITE001) | Stock: 5 | Price: $2.50 | Total: $12.50 ⚠️ LOW STOCK
[a716446] Fanta Orange (Code: FANTA001) | Stock: 100 | Price: $2.00 | Total: $200.00
=======================================
```

## 🧪 Probar la API

### Con Postman

1. Descarga [Postman](https://www.postman.com/downloads/)
2. Crea una nueva Request
3. Selecciona el método HTTP (GET, POST, PUT, DELETE)
4. Ingresa la URL: `http://localhost:8080/api/products`
5. Para POST y PUT, añade el Body en JSON
6. Presiona "Send"

### Con cURL (Terminal)

```bash
# GET todos los productos
curl http://localhost:8080/api/products

# POST crear producto
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Coca-Cola","code":"COCA001","quantity":50,"price":2.50,"minimumStock":10}'

# GET por código
curl http://localhost:8080/api/products/code/COCA001

# PUT actualizar stock
curl -X PUT "http://localhost:8080/api/products/COCA001?quantity=30"

# DELETE eliminar
curl -X DELETE http://localhost:8080/api/products/COCA001
```

## 🗄️ Base de Datos

### H2 Console

H2 proporciona una consola web para ver la base de datos en tiempo real:

```
URL: http://localhost:8080/h2-console
Usuario: sa
Contraseña: (vacío)
```

**Tabla `productos`:**
```sql
CREATE TABLE productos (
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  code VARCHAR(255) NOT NULL UNIQUE,
  quantity INT NOT NULL,
  price DOUBLE NOT NULL,
  minimum_stock INT NOT NULL
)
```

## 📁 Estructura del Proyecto

```
stockflow-api/
├── src/
│   └── main/
│       ├── java/com/stockflow/
│       │   ├── StockflowApiApplication.java
│       │   ├── model/
│       │   │   └── Producto.java
│       │   ├── service/
│       │   │   └── InventarioService.java
│       │   ├── repository/
│       │   │   └── ProductoRepository.java
│       │   └── controller/
│       │       └── ProductoController.java
│       └── resources/
│           └── application.yml
├── pom.xml
├── .gitignore
└── README.md
```

## ⚙️ Configuración

### `application.yml`

```yaml
server:
  port: 8080

spring:
  application:
    name: stockflow-api
  datasource:
    url: jdbc:h2:mem:stockflowdb
    driver-class-name: org.h2.Driver
    username: sa
    password: 
  jpa:
    hibernate:
      ddl-auto: create-drop
```

### Cambiar a MySQL

Para usar MySQL en lugar de H2:

1. Añade la dependencia en `pom.xml`:
```xml
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.33</version>
</dependency>
```

2. Modifica `application.yml`:
```yaml
datasource:
  url: jdbc:mysql://localhost:3306/stockflow
  driver-class-name: com.mysql.cj.jdbc.Driver
  username: root
  password: tucontraseña
```

## 🎓 Conceptos Spring Boot Aplicados

- ✅ `@RestController` — Crear endpoints REST
- ✅ `@RequestMapping` — Mapear rutas base
- ✅ `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` — Verbos HTTP
- ✅ `@Autowired` — Inyección de dependencias
- ✅ `@Entity` — Mapeo ORM a tabla
- ✅ `@Repository` — Interfaz para acceso a datos
- ✅ `@Service` — Lógica de negocio
- ✅ `ResponseEntity` — Respuestas HTTP con códigos de estado
- ✅ `JpaRepository` — CRUD automático

## 🗺️ Roadmap

| Fase | Estado | Descripción |
|---|---|---|
| **Fase 1** | ✅ Completa | Aplicación de consola |
| **Fase 2** | ✅ Completa | API REST con Spring Boot y H2 |
| **Fase 3** | ⏳ Próxima | Frontend React |

## 👨‍💻 Autor

**Edison Monsalve Mesa**  
- Correo: edisonmonsalve21@gmail.com
- Teléfono: +57 314 563 40 68
- GitHub: [github.com/edisonmonsalve](https://github.com)

Tecnólogo en Análisis y Desarrollo de Software — SENA

## 📝 Licencia

MIT License - Libre para usar, modificar y distribuir.

## 🤝 Contribuciones

1. Fork el repositorio
2. Crea una rama: `git checkout -b feature/mejora`
3. Commit: `git commit -m "Añade mejora"`
4. Push: `git push origin feature/mejora`
5. Pull Request

## 📞 Soporte

- 📧 Email: edisonmonsalve21@gmail.com
- 🐛 Issues: [GitHub Issues](https://github.com)
- 💬 Discussions: [GitHub Discussions](https://github.com)

---

**Made with ❤️ by Edison Monsalve | June 2026**

⭐ Si te fue útil, deja una star en GitHub!
