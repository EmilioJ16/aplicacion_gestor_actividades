# Sporting Activities Web Application

Aplicación web desarrollada en Java para la gestión de actividades deportivas de la Universidad Carlos III de Madrid, utilizando **Servlets, JSP, Apache Tomcat y MySQL**.

## Descripción

El proyecto permite gestionar actividades deportivas desde dos perfiles principales:

- **Usuario registrado**
- **Gestor**

La aplicación incluye funcionalidades de registro, autenticación, consulta de actividades e inscripción, además de un acceso diferenciado para el gestor con opciones de administración.

## Funcionalidades principales

### Usuario
- Registro de nuevos usuarios
- Confirmación de datos de registro
- Inicio y cierre de sesión
- Consulta de actividades disponibles
- Consulta de pabellones
- Búsqueda de actividades según distintos criterios
- Inscripción en actividades
- Baja de actividades ya inscritas
- Visualización de actividades del usuario

### Gestor
- Acceso mediante una URL diferente
- Autenticación con contraseña de gestor
- Listado de todas las actividades
- Añadir nuevas actividades
- Editar actividades existentes

## Tecnologías utilizadas

- **Java**
- **JSP**
- **Servlets**
- **Apache Tomcat 9**
- **MySQL**
- **HTML/CSS**

## Estructura general del proyecto

```text
aplicacion_gestor_actividades/
│
├── index.html
├── register.jsp
├── managerLogin.jsp
├── manager.jsp
├── addActivity.jsp
├── editActivity.jsp
├── WEB-INF/
│   ├── web.xml
│   ├── lib/
│   └── classes/
│       ├── *.java
│       ├── *.class
│       └── activities/db/
```

## Requisitos

Para ejecutar el proyecto es necesario tener instalado:

* Java JDK
* Apache Tomcat 9
* MySQL Server
* MySQL Connector/J

## Configuración

1. Crear la base de datos `sporting_manager`.
2. Crear las tablas necesarias:
   * `CLIENTS`
   * `REGISTRATIONS`
   * `PAVILLIONS`
   * `ACTIVITIES`
3. Configurar en `DBInteraction.java` los datos de conexión a MySQL.
4. Desplegar el proyecto en `webapps` de Tomcat.
5. Compilar los servlets y clases Java dentro de `WEB-INF/classes`.

## Ejecución

Una vez desplegado el proyecto en Tomcat, la aplicación puede abrirse desde:

```text
http://localhost:8080/aplicacion_gestor_actividades/
```

## Notas

* El acceso del gestor se realiza desde una URL separada.
* El proyecto está orientado a una práctica académica.
* Algunas configuraciones, como las credenciales de la base de datos, deben adaptarse al entorno local.

## Autores

* [Emilio José Manchado Barquero](https://www.linkedin.com/in/emilio-jos%C3%A9-manchado-barquero/)
* [Manuel Garde](https://www.linkedin.com/in/manuelgarde02/)
