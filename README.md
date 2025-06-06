# Sistema de Gestión Organizacional de Proyectos (OPM) y PMO

Este sistema permite la gestión integral de proyectos organizacionales, incluyendo actas de constitución, evaluaciones y seguimiento de proyectos.

## Características Principales

- **Gestión de Proyectos**: Creación, edición y seguimiento de proyectos
- **Actas de Constitución**: Generación y gestión de actas de constitución
- **Evaluaciones**: Sistema de evaluación de proyectos con criterios personalizables
- **Dashboard**: Visualización de estadísticas y gráficos de rendimiento
- **Generación de Documentos**: Exportación de documentos en formato Word
- **Interfaz Moderna**: Frontend desarrollado con Vaadin para Java 2025

## Tecnologías Utilizadas

- **Backend**: Spring Boot 3.5.0
- **Frontend**: Vaadin 24.3.5 (Java 2025)
- **Base de Datos**: H2 Database (en memoria)
- **Java**: JDK 17
- **Gestión de Dependencias**: Maven
- **Seguridad**: Spring Security
- **Documentación API**: SpringDoc OpenAPI

## Requisitos Previos

- JDK 17 o superior
- Maven 3.8 o superior
- Git (opcional, para clonar el repositorio)

## Instalación

### Opción 1: Desde el archivo ZIP

1. Descomprima el archivo ZIP en una ubicación de su preferencia
2. Abra una terminal y navegue hasta la carpeta del proyecto
3. Ejecute el siguiente comando para compilar y ejecutar la aplicación:

```bash
mvn spring-boot:run
```

### Opción 2: Desde GitHub

1. Clone el repositorio:

```bash
git clone https://github.com/su-usuario/sistema-gestion-proyectos.git
cd sistema-gestion-proyectos
```

2. Compile y ejecute la aplicación:

```bash
mvn spring-boot:run
```

## Acceso a la Aplicación

Una vez iniciada la aplicación, puede acceder a ella a través de su navegador web:

- **URL**: http://localhost:8080
- **Credenciales por defecto**:
  - Usuario: admin
  - Contraseña: admin

## Estructura del Proyecto

```
sistema-gestion-proyectos/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── gestion/
│   │   │           └── proyectos/
│   │   │               ├── config/          # Configuraciones
│   │   │               ├── controller/      # Controladores REST
│   │   │               ├── dto/             # Objetos de transferencia de datos
│   │   │               ├── exception/       # Manejo de excepciones
│   │   │               ├── frontend/        # Vistas y componentes Vaadin
│   │   │               ├── model/           # Entidades JPA
│   │   │               ├── repository/      # Repositorios JPA
│   │   │               ├── security/        # Configuración de seguridad
│   │   │               ├── service/         # Servicios de negocio
│   │   │               └── util/            # Utilidades
│   │   └── resources/
│   │       ├── static/                      # Recursos estáticos
│   │       ├── templates/                   # Plantillas
│   │       └── application.properties       # Configuración de la aplicación
│   └── test/                                # Pruebas unitarias e integración
└── pom.xml                                  # Configuración de Maven
```

## Uso de la Aplicación

### Dashboard

La página principal muestra un dashboard con estadísticas y gráficos sobre los proyectos registrados en el sistema.

### Gestión de Proyectos

En esta sección puede:
- Ver la lista de proyectos
- Crear nuevos proyectos
- Editar proyectos existentes
- Eliminar proyectos

### Actas de Constitución

Permite:
- Crear actas de constitución para los proyectos
- Editar actas existentes
- Descargar actas en formato Word

### Evaluaciones

Facilita:
- Crear evaluaciones para los proyectos
- Definir criterios de evaluación
- Generar reportes de evaluación

## Base de Datos

El sistema utiliza una base de datos H2 en memoria por defecto. Puede acceder a la consola H2 en:
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:pmodb
- Usuario: sa
- Contraseña: (dejar en blanco)

Para utilizar una base de datos persistente, modifique la configuración en `application.properties`.

## API REST

El sistema proporciona una API REST completa para integración con otros sistemas. La documentación de la API está disponible en:
- URL: http://localhost:8080/swagger-ui.html

## Contribución

Si desea contribuir al proyecto:

1. Haga un fork del repositorio
2. Cree una rama para su funcionalidad (`git checkout -b feature/nueva-funcionalidad`)
3. Realice sus cambios y haga commit (`git commit -am 'Añadir nueva funcionalidad'`)
4. Haga push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Cree un Pull Request

## Licencia

Este proyecto está licenciado bajo la Licencia MIT - vea el archivo LICENSE para más detalles.

## Contacto

Para preguntas o soporte, contacte a: [su-email@ejemplo.com]
