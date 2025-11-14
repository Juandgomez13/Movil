# 📋 Pruebas CRUD Postman - API Movil

## 🚀 Configuración Inicial

### 1. Importar Colección y Environment
1. Abre Postman
2. Importa el archivo `Movil_API_Tests.postman_collection.json`
3. Importa el archivo `Movil_API_Environment.postman_environment.json`
4. Selecciona el environment "Movil API - Development"

### 2. Variables de Environment
El environment incluye las siguientes variables configurables:
- `base_url`: URL base del API (por defecto: http://localhost:8080)
- `admin_username` / `admin_password`: Credenciales de administrador
- `coordinador_username` / `coordinador_password`: Credenciales de coordinador
- Variables dinámicas de IDs que se actualizan automáticamente durante las pruebas

## 🔐 Autenticación

### Roles Disponibles
- **ADMIN**: Acceso completo (crear, leer, actualizar, eliminar)
- **COORDINADOR**: Acceso limitado (crear, leer, actualizar - no puede eliminar)

### Proceso de Login
1. Ejecuta primero una de las pruebas de Login en la carpeta "🔐 Authentication"
2. El token JWT se guarda automáticamente en la variable `jwt_token`
3. Todas las demás requests usan automáticamente este token

## 📝 Estructura de Pruebas CRUD

Cada entidad (Users, Categories, Subcategories, Products) incluye:

### ✅ Operaciones Básicas
- **Create**: Crear nueva entidad
- **Get All**: Obtener todas las entidades
- **Get by ID**: Obtener entidad específica por ID
- **Update**: Actualizar entidad existente
- **Deactivate**: Desactivar entidad (cambiar `active` a `false`)
- **Delete**: Eliminar entidad permanentemente

### 🧪 Tests Automáticos
Cada request incluye tests que verifican:
- Status codes correctos (200, 404, 403, etc.)
- Estructura de respuesta
- Validación de datos
- Actualización automática de variables de environment

## 📂 Orden de Ejecución Recomendado

### 1. Setup Inicial
```
🔐 Authentication > Login - Admin
```

### 2. Flujo Completo de Pruebas
```
1. Categories: Create → Get All → Update → Deactivate
2. Subcategories: Create → Get All → Get by ID → Update → Deactivate  
3. Products: Create → Get All → Get by ID → Update → Deactivate
4. Users: Create → Get All → Get by ID → Update → Deactivate → Delete
```

### 3. Limpieza (opcional)
```
- Delete Product
- Delete Subcategory  
- Delete Category
- Delete User
```

## 🔧 Configuración de Variables Dinámicas

Las pruebas usan variables automáticas de Postman:
- `{{$timestamp}}`: Genera timestamp único
- `{{$randomInt}}`: Genera número aleatorio
- Variables de collection que se actualizan automáticamente con IDs

## 📋 Datos de Prueba

### Usuarios
```json
{
    "username": "test_user_{{$timestamp}}",
    "password": "password123", 
    "email": "test{{$timestamp}}@example.com",
    "role": "COORDINADOR",
    "active": true
}
```

### Categorías
```json
{
    "name": "Test Category {{$timestamp}}",
    "description": "Descripción de prueba para categoría",
    "active": true
}
```

### Subcategorías
```json
{
    "name": "Test Subcategory {{$timestamp}}",
    "description": "Descripción de prueba para subcategoría", 
    "active": true,
    "category": {
        "id": "{{category_id}}"
    }
}
```

### Productos
```json
{
    "name": "Test Product {{$timestamp}}",
    "description": "Descripción de prueba para producto",
    "price": 99.99,
    "stock": 100,
    "active": true,
    "category": {
        "id": "{{category_id}}"
    },
    "subcategory": {
        "id": "{{subcategory_id}}"
    }
}
```

## 🚨 Casos de Error a Validar

### Autenticación
- Login con credenciales incorrectas
- Acceso sin token JWT
- Token JWT expirado
- Acceso con permisos insuficientes

### Validación de Datos
- Campos requeridos faltantes
- Formatos de email inválidos
- IDs de entidades que no existen
- Nombres duplicados (unique constraints)

### Permisos
- COORDINADOR intentando eliminar (debe fallar)
- Acceso a recursos sin autorización adecuada

## 📊 Ejecución con Collection Runner

Para ejecutar todas las pruebas automáticamente:

1. Click en la colección "Movil API - CRUD Tests"
2. Click "Run Collection"
3. Selecciona el environment "Movil API - Development"  
4. Configura el orden: Authentication → Users → Categories → Subcategories → Products
5. Click "Run Movil API - CRUD Tests"

## 🔍 Troubleshooting

### Errores Comunes
- **401 Unauthorized**: Verifica que el token JWT está configurado
- **403 Forbidden**: Verifica que el usuario tiene los permisos correctos
- **404 Not Found**: Verifica que los IDs de entidades existen
- **Connection Error**: Verifica que el backend está ejecutándose en `localhost:8080`

### Verificación del Backend
```bash
# Verificar que el backend está ejecutándose
curl http://localhost:8080/api/auth/login

# Debería retornar una respuesta JSON
```

## 📈 Métricas de Pruebas

El Collection Runner mostrará:
- Total de requests ejecutadas
- Tests pasados/fallados
- Tiempo de ejecución
- Detalles de respuestas para debugging

---

**Nota**: Estas pruebas están diseñadas para un entorno de desarrollo. Para producción, ajustar URLs y credenciales según corresponda.