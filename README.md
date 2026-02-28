# DOSW_ParcialT1_GinaGarcia
**Diagrama de contexto**
![Captura](/imagenes/diagrama%20de%20contexto.png)

**Identificar patrones**
a. Nombre del Patrón
El primero Strategy y Factory Method y Observer

b. Tipo de patrón (creacional, estructural o de comportamiento).
Hay dos tipos de patrones estructural y creacional

c. Justificación de la decisión
Strategy para procesar los diferentes formato de entrada de los sistemas externos de profesores y estudiantes y Factrory Methos para la creacion de distintos tipos de eventos. Observer para las notificaciones que nos solicitan.

# Requerimientos del Sistema

## 1. Lista general de requerimientos

El sistema de EventSync tiene los siguientes requerimientos:
### 1.1 Requerimientos funcionales

El sistema de EventSync debe tener la capacidad de:

1.Creación de Eventos por Tipo
2.Gestión de Inscripciones
3.Notificación Automática

### 1.2 Requerimientos no funcionales

El sistema de EventSync debe tener:

1.Validación de Dominio: El sistema solo debe permitir registros con correos institucionales (@escuelaing.edu.co o @mail.escuelaing.edu.co)
2.Trazabilidad de Estados: El sistema debe garantizar que un evento solo pase a estado "Confirmado" si cumple las reglas de negocio (ej. mínimo 10 inscritos en Talleres)

## 2. Diagramas de caso de uso

### 2.1 Requerimiento Funcional 1

| Campo | Descripción |
|------|-------------|
| **ID** | RF-01 |
| **Nombre del requerimiento** | Creación de las actividades a desarrollar |
| **Descripción** | El sistema debe permitir la creación de Conferencias, Talleres y Hackathons validando la duración máxima y el rol del creador (Factory Method) |
| **Precondiciones** | El usuario debe haber iniciado sesión y poseer un rol válido |  |
| **Actor** | Profesor/Administrativo|
| **Flujo principal** | 1. El actor selecciona el tipo de evento a crear. 2. El actor ingresa título, duración y fecha. 3. El sistema valida si la duración y el rol son permitidos. 4. El sistema confirma la creación del evento.|
| **Diagrama de caso de uso** | ![Captura](/imagenes/diagrama%20de%20caso%20de%20uso%201.png)|
| **Poscondiciones** | Se espera como resultado que el profesor o administraivo pueda crerar la conferencia o taller con la duracion que es permitida|


### 2.2 Requerimiento Funcional 2

| Campo | Descripción |
|------|-------------|
| **ID** | RF-02 |
| **Nombre del requerimiento** | Registro de asistentes|
| **Descripción** | El sistema debe registrar asistentes validando el cupo máximo y el tipo de usuario permitido (Estudiante/Profesor/Administrativo) según el evento |
| **Precondiciones** | Debe existir un evento con al menos un usuario inscrito |
| **Actor** | Profesor y Asistentes|
| **Flujo principal** | 1. El Profesor registra a los asistentes. 2. El sistema verificar el cupo maximo. 3. El sistema verifica si es un tipo de usuario permitido|
| **Diagrama de caso de uso** | ![Captura](/imagenes/diagrama%20caso%20de%20uso%202.png)|
| **Poscondiciones** | Se registra adeacudamente los asistentes segun el tipo de todos |

### 2.3 Requerimiento Funcional 3

| Campo | Descripción |
|------|-------------|
| **ID** | RF-03 |
| **Nombre del requerimiento** | Notificación automática por cambio de cronograma |
| **Descripción** | El sistema debe notificar a todos los inscritos cuando ocurra un cambio en la fecha o hora del evento (Observer) |
| **Precondiciones** | Debe existir un evento tambien con un estudiante inscrito|
| **Actor** | Profesor (Quien modifica) / Estudiante (Quien recibe) |
| **Flujo principal** | 1. El Profesor modifica la fecha/hora del evento.2. El sistema actualiza el registro del evento. 3. El sistema (Observer) dispara una notificación a la lista de suscritos. |
| **Diagrama de caso de uso** |![Captura](/imagenes/diagrama%20caso%20de%20uso%203.png) |
| **Poscondiciones** | Todos los inscritos reciben un mensaje con la nueva información del evento |

**Casos de Uso e Historia de Usuario**

Caso de Uso: Crear Evento Académico

Seleccionamos los dos más importantes: Crear Evento e Inscribir Asistente.

Historia de Usuario 1: Crear Conferencia
Como profesor quiero crear una conferencia de 120 minutos para que los estudiantes puedan verla en la plataforma
Criterio de Aceptación: El sistema debe rechazar la creación si el profesor intenta que dure más de 180 min

Historia de usuario 2: Registro de asistentes
Como profesor quiero registrar mis ususarios en la plataforma para poder ingresar a los usuarios permitidos 
Criterio de Aceptación: El sistema rechazara si el cupo esta en su maximo y tambien si el tipo de usuario es permitido.

Historia de usuario 3: Notificacion automatica
Como profesor quiero que a mis estudiantes tengan una notificacion del evento para que puedan tener actualización del mismo.
Criterio de Aceptación: El sistema rech

Descomposición de Tareas (Épica: Notificaciones)
Historia:Como usuario inscrito, quiero recibir un correo cuando el evento cambie de hora para reprogramar mi agenda
Tarea 1: Crear interfaz Observer con el método actualizar().
Tarea 2: Implementar lógica en la clase Evento para mantener una lista de suscritos.
Tarea 3: Disparar el método notificar() dentro del setter setFechaHora().

**Diagrama de Clases y SOLID**
Principios SOLID a aplicar

S (Single Responsibility): Crear clases separadas para la lógica de validación de usuarios y la gestión de eventos.
O (Open/Closed): El sistema debe permitir agregar nuevos tipos de eventos (ej. "Seminario") sin modificar el código de inscripción existente.
D (Dependency Inversion): Depender de abstracciones (ej. una interfaz Notificador) en lugar de implementaciones concretas de consola.
Encapsulamiento: El cliente (la interfaz de usuario o el main) no necesita saber cómo se construye internamente cada evento, solo pide un "Evento" por su nombre.





