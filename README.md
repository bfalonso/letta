LETTA
==========

LETTA es una aplicación cuya finalidad es gestionar micro-quedadas sobre cualquier temática.

## Ejecución con Maven
La configuración de Maven ha sido preparada para permitir varios tipos de
ejecución.

### Ejecución de la aplicación con Tomcat y MySQL

El proyecto está configurado para poder ejecutar la aplicación sin tener que
realizar ninguna configuración adicional salvo tener disponible un servidor
MySQL en local.

El fichero del proyecto 'db/mysql-with-inserts.sql' contiene
todas las consultas necesarias para crear la base de datos y el usuario
requeridos, con datos de ejemplo. Por lo tanto, podemos
configurar inicialmente la base de datos con el siguiente
comando (desde la raíz el proyecto):

* Con datos: `mysql -u root -p < db/mysql-with-inserts.sql`

Una vez configurada la base de datos podemos lanzar la ejecución con el comando:

`mvn -Prun-tomcat-mysql -DskipTests=true package cargo:run`

La aplicación se servirá en la URL local: http://localhost:9080/letta

Para detener la ejecución podemos utilizar `Ctrl+C`.

### Ejecución de la aplicación con Tomcat y MySQL con redespliegue automático

Durante el desarrollo es interesante que la apliación se redespliegue de forma
automática cada vez que se hace un cambio. Para ello podemos utilizar el
siguiente comand:

`mvn -Prun-tomcat-mysql -DskipTests=true package cargo:start fizzed-watcher:run`

La aplicación se servirá en la URL local: http://localhost:9080/letta

Para detener la ejecución podemos utilizar `Ctrl+C`.

### Construcción con tests de unidad e integración

En esta construcción se ejecutarán todos los tests relacionados con el backend:

* **Unidad**: se utilizan para testear las entidades y las capas DAO y REST de
forma aislada.
* **Integración**: se utilizan para testear las capas REST y DAO de forma
integrada. Para este tipo de pruebas se utiliza una base de datos HSQL en
memoria.

El comando para lanzar esta construcción es:

`mvn install`

### Construcción con tests de unidad, integración y aceptación

Esta construcción es similar a la previa, añadiendo las **pruebas de
aceptación**, que comprueban que las fucionalidades de la aplicación están
correctamente implementadas.

En estas pruebas se descarga y arranca el un servidor Tomcat 8 en el que se
despliega la aplicación configurada para utilizar una base de datos HSQL.

`mvn -Pacceptance-tests-cargo install`
