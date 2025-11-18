# Documentación API Backend Movil - Para Postman

## URL Base
```
http://localhost:8080
```

## Autenticación

### Header de Autenticación
Todos los endpoints (excepto `/api/auth/login`) requieren el header:
```
Authorization: Bearer {{jwt_token}}
```

---

## 1. AUTENTICACIÓN

### POST /api/auth/login
**Descripción:** Iniciar sesión y obtener token JWT.

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Respuesta exitosa (200):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "username": "admin",
    "email": "admin@app.com",
    "role": "ADMIN",
    "active": true
  }
}
```

**Script de test (copiar en Tests de Postman):**
```javascript
var json = pm.response.json();
pm.environment.set("jwt_token", json.token);
```

**Usuarios disponibles:**
- Usuario: `admin` / Password: `admin123` (Rol: ADMIN)
- Usuario: `coordinador` / Password: `coord123` (Rol: COORDINADOR)

---

## 2. USUARIOS

### GET /api/users
**Descripción:** Listar todos los usuarios.

**Roles requeridos:** ADMIN o COORDINADOR

**Headers:**
```
Authorization: Bearer {{jwt_token}}
```

**Respuesta exitosa (200):**
```json
[
  {
    "id": 1,
    "username": "admin",
    "email": "admin@app.com",
    "role": "ADMIN",
    "active": true,
    "createdAt": "2025-11-18T15:00:00"
  }
]
```

---

### GET /api/users/{id}
**Descripción:** Obtener un usuario por ID.

**Roles requeridos:** ADMIN o COORDINADOR

**Headers:**
```
Authorization: Bearer {{jwt_token}}
```

**Ejemplo:** `GET /api/users/1`

**Respuesta exitosa (200):**
```json
{
  "id": 1,
  "username": "admin",
  "email": "admin@app.com",
  "role": "ADMIN",
  "active": true,
  "createdAt": "2025-11-18T15:00:00"
}
```

---

### POST /api/users
**Descripción:** Crear un nuevo usuario.

**Roles requeridos:** ADMIN o COORDINADOR

**Headers:**
```
Authorization: Bearer {{jwt_token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "username": "nuevo_usuario",
  "password": "password123",
  "email": "usuario@ejemplo.com",
  "role": "COORDINADOR",
  "active": true
}
```

**IMPORTANTE:** El campo es `role` (singular), no `roles` (plural). Valores válidos: `ADMIN`, `COORDINADOR`.

**Respuesta exitosa (200):**
```json
{
  "id": 3,
  "username": "nuevo_usuario",
  "email": "usuario@ejemplo.com",
  "role": "COORDINADOR",
  "active": true,
  "createdAt": "2025-11-18T15:30:00"
}
```

---

### PUT /api/users/{id}
**Descripción:** Actualizar un usuario existente.

**Roles requeridos:** ADMIN o COORDINADOR

**Headers:**
```
Authorization: Bearer {{jwt_token}}
Content-Type: application/json
```

**Ejemplo:** `PUT /api/users/3`

**Body (JSON):**
```json
{
  "username": "usuario_actualizado",
  "email": "nuevo@ejemplo.com",
  "role": "ADMIN",
  "active": false
}
```

**Respuesta exitosa (200):**
```json
{
  "id": 3,
  "username": "usuario_actualizado",
  "email": "nuevo@ejemplo.com",
  "role": "ADMIN",
  "active": false,
  "createdAt": "2025-11-18T15:30:00"
}
```

---

### DELETE /api/users/{id}
**Descripción:** Eliminar un usuario.

**Roles requeridos:** ADMIN

**Headers:**
```
Authorization: Bearer {{jwt_token}}
```

**Ejemplo:** `DELETE /api/users/3`

**Respuesta exitosa (200):**
```json
{
  "message": "Usuario eliminado exitosamente"
}
```

---

## 3. CATEGORÍAS

### GET /api/categories
**Descripción:** Listar todas las categorías.

**Autenticación requerida:** Sí (cualquier usuario autenticado)

**Headers:**
```
Authorization: Bearer {{jwt_token}}
```

**Respuesta exitosa (200):**
```json
[
  {
    "id": 1,
    "name": "Electrónica",
    "createdAt": "2025-11-18T15:00:00"
  }
]
```

**Script de test (para guardar ID):**
```javascript
var json = pm.response.json();
if (json.length > 0) {
    pm.environment.set("category_id", json[0].id);
}
```

---

### GET /api/categories/{id}
**Descripción:** Obtener una categoría por ID.

**Autenticación requerida:** Sí

**Headers:**
```
Authorization: Bearer {{jwt_token}}
```

**Ejemplo:** `GET /api/categories/1`

**Respuesta exitosa (200):**
```json
{
  "id": 1,
  "name": "Electrónica",
  "createdAt": "2025-11-18T15:00:00"
}
```

---

### POST /api/categories
**Descripción:** Crear una nueva categoría.

**Roles requeridos:** ADMIN

**Headers:**
```
Authorization: Bearer {{jwt_token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "name": "Electrónica"
}
```

**Respuesta exitosa (200):**
```json
{
  "id": 1,
  "name": "Electrónica",
  "createdAt": "2025-11-18T15:00:00"
}
```

**Script de test:**
```javascript
var json = pm.response.json();
pm.environment.set("category_id", json.id);
```

---

### PUT /api/categories/{id}
**Descripción:** Actualizar una categoría.

**Roles requeridos:** ADMIN

**Headers:**
```
Authorization: Bearer {{jwt_token}}
Content-Type: application/json
```

**Ejemplo:** `PUT /api/categories/1`

**Body (JSON):**
```json
{
  "name": "Tecnología"
}
```

**Respuesta exitosa (200):**
```json
{
  "id": 1,
  "name": "Tecnología",
  "createdAt": "2025-11-18T15:00:00"
}
```

---

### DELETE /api/categories/{id}
**Descripción:** Eliminar una categoría.

**Roles requeridos:** ADMIN

**Headers:**
```
Authorization: Bearer {{jwt_token}}
```

**Ejemplo:** `DELETE /api/categories/1`

**Respuesta exitosa (200):**
```json
{
  "message": "Categoría eliminada exitosamente"
}
```

---

## 4. PRODUCTOS

### GET /api/products
**Descripción:** Listar todos los productos.

**Autenticación requerida:** Sí

**Headers:**
```
Authorization: Bearer {{jwt_token}}
```

---

### GET /api/products/{id}
**Descripción:** Obtener un producto por ID.

**Autenticación requerida:** Sí

**Headers:**
```
Authorization: Bearer {{jwt_token}}
```

---

### POST /api/products
**Descripción:** Crear un nuevo producto.

**Roles requeridos:** ADMIN

**Headers:**
```
Authorization: Bearer {{jwt_token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "name": "Laptop HP",
  "description": "Laptop de alta gama",
  "price": 2500.00,
  "stock": 10,
  "category": {
    "id": 1
  },
  "subcategory": {
    "id": 1
  }
}
```

**IMPORTANTE:** Debes enviar `category` y `subcategory` como objetos con solo el `id`, NO como IDs directos. Primero crea una categoría y subcategoría, guarda sus IDs y úsalos aquí.

**Script de test:**
```javascript
var json = pm.response.json();
pm.environment.set("product_id", json.id);
```

---

### PUT /api/products/{id}
**Descripción:** Actualizar un producto.

**Roles requeridos:** ADMIN

**Headers:**
```
Authorization: Bearer {{jwt_token}}
Content-Type: application/json
```

---

### DELETE /api/products/{id}
**Descripción:** Eliminar un producto.

**Roles requeridos:** ADMIN

**Headers:**
```
Authorization: Bearer {{jwt_token}}
```

---

## 5. SUBCATEGORÍAS

### GET /api/subcategories
**Descripción:** Listar todas las subcategorías.

**Autenticación requerida:** Sí

**Headers:**
```
Authorization: Bearer {{jwt_token}}
```

---

### GET /api/subcategories/{id}
**Descripción:** Obtener una subcategoría por ID.

**Autenticación requerida:** Sí

**Headers:**
```
Authorization: Bearer {{jwt_token}}
```

---

### POST /api/subcategories
**Descripción:** Crear una nueva subcategoría.

**Roles requeridos:** ADMIN

**Headers:**
```
Authorization: Bearer {{jwt_token}}
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "name": "Laptops",
  "category": {
    "id": 1
  }
}
```

**IMPORTANTE:** Debes enviar `category` como un objeto con solo el `id`, NO como `categoryId`.

**Script de test:**
```javascript
var json = pm.response.json();
pm.environment.set("subcategory_id", json.id);
```

---

### PUT /api/subcategories/{id}
**Descripción:** Actualizar una subcategoría.

**Roles requeridos:** ADMIN

**Headers:**
```
Authorization: Bearer {{jwt_token}}
Content-Type: application/json
```

---

### DELETE /api/subcategories/{id}
**Descripción:** Eliminar una subcategoría.

**Roles requeridos:** ADMIN

**Headers:**
```
Authorization: Bearer {{jwt_token}}
```

---

## 6. ESTADÍSTICAS

### GET /api/stats
**Descripción:** Obtener estadísticas generales del sistema.

**Autenticación requerida:** Sí

**Headers:**
```
Authorization: Bearer {{jwt_token}}
```

**Respuesta exitosa (200):**
```json
{
  "totalUsers": 2,
  "totalCategories": 5,
  "totalProducts": 15,
  "totalSubcategories": 8
}
```

---

## Variables de entorno de Postman

Crea un entorno en Postman con las siguientes variables:

1. `jwt_token` - Se guarda automáticamente después del login
2. `category_id` - Se guarda después de crear una categoría
3. `product_id` - Se guarda después de crear un producto
4. `subcategory_id` - Se guarda después de crear una subcategoría
5. `user_id` - Se guarda después de crear un usuario

---

## Flujo de pruebas recomendado

1. **Login** - `POST /api/auth/login` (guarda token automáticamente)
2. **Crear categoría** - `POST /api/categories` con `{"name": "Electrónica"}` (guarda category_id)
3. **Listar categorías** - `GET /api/categories`
4. **Obtener categoría por ID** - `GET /api/categories/{{category_id}}`
5. **Crear subcategoría** - `POST /api/subcategories` con `{"name": "Laptops", "category": {"id": {{category_id}}}}`
6. **Crear producto** - `POST /api/products` con:
   ```json
   {
     "name": "Laptop HP",
     "description": "Laptop de alta gama",
     "price": 2500.00,
     "stock": 10,
     "category": {"id": 1},
     "subcategory": {"id": 1}
   }
   ```
7. **Crear usuario** - `POST /api/users` (**Importante:** usa `role`, no `roles`)
8. **Listar usuarios** - `GET /api/users`
9. **Obtener estadísticas** - `GET /api/stats`

---

## Errores comunes

### Error 403 Forbidden
- **Causa:** Token JWT no válido o expirado, o no tienes el rol necesario.
- **Solución:** Vuelve a hacer login para obtener un nuevo token.

### Error 401 Unauthorized
- **Causa:** No se envió el header `Authorization` o el token es inválido.
- **Solución:** Verifica que el header esté presente y tenga el formato correcto.

### Error "Column 'role' cannot be null"
- **Causa:** Estás enviando `"roles": ["ADMIN"]` en lugar de `"role": "ADMIN"`.
- **Solución:** Usa `role` (singular) con un solo valor de texto: `"role": "ADMIN"`.

---

## Notas importantes

1. **El backend ya está corriendo con la configuración de seguridad original.**
2. **Todos los endpoints requieren autenticación JWT excepto `/api/auth/login`.**
3. **Los roles válidos son:** `ADMIN` y `COORDINADOR`.
4. **El campo para crear usuarios es `role` (singular), no `roles` (plural).**
5. **ADMIN tiene acceso completo; COORDINADOR puede ver y crear usuarios/categorías/productos pero no eliminarlos.**
