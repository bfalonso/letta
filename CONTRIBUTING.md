# ¿Cómo contribuir a este proyecto?

## Tabla de contenido
  * [1. Empezando](#1-empezando)
  * [2. Desarrollo de una tarea](#2-desarrollo-de-una-tarea)
  * [3. Estructura del proyecto](#3-estructura-del-proyecto)
  * [4. Entorno de desarrollo](#4-entorno-de-desarrollo)
  * [5. Configuración de un entorno de desarrollo](#5-configuración-de-un-entorno-de-desarrollo)
    * [5.1. Ejecución en un tomcat local](#51-ejecución-en-un-tomcat-local)
  * [6. Control de versiones (Git)](#6-control-de-versiones-git)
    * [6.1. Commits con errores de construcción](#61-commits-con-errores-de-construcción)
    * [6.2. Push con commits nuevos en el servidor remoto](#62-push-con-commits-nuevos-en-el-servidor-remoto)
    * [6.3. Pull con cambios locales no commiteados ](#63-pull-con-cambios-locales-no-commiteados)
  * [7. Tests](#7-tests)
    * [7.1 Tests por módulo](#71-tests-por-módulo)
    * [7.2 El módulo tests](#72-el-módulo-test)
    * [7.3 Ejecución de los tests](#73-ejecución-de-los-tests)
      * [7.3.1 Ejecución de los tests en Maven](#731-ejecución-de-los-tests-en-maven)
    * [7.4 Análisis de los resultados de los tests](#74-análisis-de-los-resultados-de-los-tests)
  * [8. Guía de estilo](#8-guía-de-estilo)
    * [8.1. Código fuente](#81-código-fuente)
    * [8.2. Control de versiones](#82-control-de-versiones)
  * [9. Instrucciones de ejecución](#9-instrucciones-de-ejecución)
    * [9.1 Ejecución con Maven](#91-ejecución-con-maven)
      * [9.1.1 Ejecución de la aplicación con Tomcat y MySQL](#911-ejecución-de-la-aplicación-con-tomcat-y-mysql)
      * [9.1.2 Ejecución de la aplicación con Tomcat y MySQL con redespliegue automático](#912-ejecución-de-la-aplicación-con-tomcat-y-mysql-con-redespliegue-automático)
    * [9.2 Construcción con tests de unidad e integración](#92-construcción-con-tests-de-unidad-e-integración)
    * [9.3 Construcción con tests de unidad, integración y aceptación](#93-construcción-con-tests-de-unidad-integración-y-aceptación)


## 1. Empezando

En este documento encontrarás una descripción del entorno de desarrollo y las
instrucciones para saber cómo contribuir correctamente a este proyecto.


## 2. Desarrollo de una tarea
El proceso habitual para realizar una tarea será, normalmente, el siguiente:
1. En Kunagi selecciona la tarea de la que seas responsable que deseas
desarrollar y lee bien la descripción de la misma.
2. Abre el entorno de desarrollo.
3. Verifica que te encuentras en la rama `develop`. Si no es así, cámbiate a
esta rama.
4. Haz *pull* de los últimos cambios (ver [sección 6](#6-control-de-versiones-git)).
5. Implementa la solución, incluyendo los tests (ver [sección 7](#7-tests)).
        1. Haz un *commit* con cada parte estable (completa y testeada) que
  desarrolles.
        2. Cada vez que hagas un *commit* envíalo al repositorio central para
  compartirlo con el resto del equipo (ver [sección 6](#6-control-de-versiones-git)).
        3. Si la construcción falla, sigue los pasos descritos en la
  [sección 6.4](#64-pull-con-cambios-locales-no-commiteados).
6. Cuando acabes la jornada de trabajo recuerda introducir las horas en la tarea
de Kunagi.
7. Avisar por el grupo de WhatsApp de los resultados de la integración.

En las siguientes secciones encontrarás información que te ayudará a realizar
este trabajo.


## 3. Estructura del proyecto
Este proyecto está estructurado en 4 módulos:

* **tests**:
Módulo que contiene utilidades para realizar los tests. Este módulo será
importado por el resto de módulos con el *scope* tests para hacer uso de sus
utilidades.
* **entities**:
Módulo que contiene las clases de dominio (entidades).
* **dao**:
Módulo que gestiona la persistencia de las entidades utilizando el API estándar de Java JDBC.
* **rest**:
Módulo que contiene una capa de servicios REST.
* **js/view**:
Módulo que atiende a los eventos que se producen en la capa HTML y solicita información a la capa js/dao para construír la vista HTML.
* **js/dao**:
Módulo que recibe las solicitudes de la capa js/view y solicita información al backend mediante AJAX.
* **HTML**:
Módulo que contiene las plantillas necesarias para presentar la información.


## 4. Entorno de desarrollo.
Las herramientas que componen el entorno de desarrollo son las siguientes:

* **Maven 3**: Maven es un entorno de construcción de proyectos para Java. Esta será una herramienta clave, ya que es quien dirigirá todo el proyecto. Es necesario que tengas instalado Maven 3 en tu equipo de desarrollo para poder construir el proyecto.
* **Kunagi**: Es una herramienta de gestión de proyectos Scrum. En ella encontrarás toda la información sobre las funcionalidades desarrolladas y por desarrollar, el alcance de las publicaciones, el estado de desarrollo, etc.
* **Git y Gitlab**: Git es el sistema de control de versiones que se utiliza en el proyecto. Es un sistema de control de versiones distribuido que facilita la colaboración entre desarrolladores. Es necesario que tengas instalado Git en tu sistema para poder realizar cambios en el proyecto y colaborar con el resto del equipo. Por otro lado, Gitlab es un front-end del repositorio Git común. Esta herramienta facilita la visualización de los commits y ficheros del proyecto, además de proporcionar alguna otra funcionalidad que mejora la colaboración.
* **Tomcat local**: Para poder ejecutar el servidor en tu entorno de desarrollo también será necesario que dispongas de un Tomcat local. En este proyecto, el servidor se encuentra embebido, por tanto no será necesario que instales nada.
* **MySQL**: MySQL es el sistema gestor de base de datos (SGDB) que utilizará el sistema definitivo. En la explicación de cómo ejecutar el sistema en local utilizaremos este SGBD, por lo que deberás tenerlo instalado en tu equipo.


## 5. Configuración de un entorno de desarrollo
Empezar a trabajar en el proyecto es tan sencillo como seguir los siguientes
pasos:

1. Instala Git y Maven. Si estás en un entorno Ubuntu es tan sencillo como
ejecutar `sudo apt-get install git maven`. También es recomendable que instales
algún visor de Git como `GitKraken`. Puedes descargarlo a través de este enlace https://www.gitkraken.com/download

2. Clona el repositorio Git utilizando el comando:
```bash
git clone http://sing-group.org/dt/gitlab/daa1920-teamB/letta
```

3. Instala Eclipse 4.13.0 for Enterprise Java Developers:
  1. Descarga el IDE desde https://www.eclipse.org/downloads/packages/release/2019-09/r/eclipse-ide-enterprise-java-developers
  2. Importa el proyecto en Eclipse utilizando `Import...->Existing Maven
projects`, selecciona el directorio del proyecto en `Root directory` y marca
todos los proyectos que aparezcan.

### 5.1. Ejecución en un Tomcat local.
La ejecución del proyecto en un Tomcat local requiere de un SGBD MySQL. 
Por lo tanto, para configurar un Tomcat local debes seguir los siguientes pasos:

1. Instala MySQL. Si estás en un entorno Ubuntu es tan sencillo como ejecutar
`sudo apt-get install mysql`. Durante la instalación te pedirán que introduzcas
la clave de *root*, que es importante que recuerdes.
2. Importa la base de datos en MySQL. En el directorio `db` del proyecto
`letta` están el*script* de creación de la
base de datos. El *script* `mysql-with-inserts.sql` contiene la creación completa de la
base de datos, incluyendo la creación del esquema, del usuario usado por la
aplicación y de algunos datos de prueba. Por lo tanto, si estás en un sistema Ubuntu, puedes realizar la
importación desde la raíz del proyecto con el siguiente comando:
```bash
mysql -u root -p < db/mysql-with-inserts.sql
```
Con esto ya estaría configurado el Tomcat local y ejecutar es tan sencillo
como invocar el siguiente comando desde el directorio del proyecto:
```
mvn -Prun -DskipTests=true package cargo:start fizzed-watcher:run
```

## 6. Control de versiones (Git)
El modelo de control de versiones que utilizaremos inicialmente será muy
sencillo ya que solo utilizaremos dos ramas:
* `master`: A esta rama solo se enviarán los *commits* cuando se llegue a una
versión estable y publicable (una *release*). Estas versiones deberán estar
etiquetadas con el número de versión correspondiente.
* `develop`: Esta será la rama principal de trabajo. Los *commits* que se envíen
deben ser estables, lo que supone que el código debe incluir tests y todos deben
superarse existosamente al construir la aplicación en local.

### 6.1. *Commits* con errores de construcción
En el caso de que una **construcción falle** es muy importante **deshacer el último *commit*
para volver a un estado estable**.

Aunque existen varias formas de hacer esto, la forma más directa es:
```
git push origin +HEAD^:develop
```
Este comando fuerza a que la rama `develop` remota se sitúe en el *commit*
anterior a `HEAD`, ya que `HEAD` es el *commit* conflictivo. El *commit* seguirá
existiendo en local y se espera que tras corregir los errores se realice un
`git commit --amend`.
Si se desea descartar el *commit* local pero mantener el estado de los ficheros,
puede utilizarse un `git reset --mixed HEAD^`.

### 6.2. *Push* con *commits* nuevos en el servidor remoto
Si se desea hacer un *push* a un servidor remoto en el cual hay *commits* que
nuevos que no tenemos en local, entonces Git muestra un error en el que nos
indica que debemos hacer un *pull* antes de poder hacer *push*.

Dado que no nos interesa tener que añadir un *commit* de *merge* adicional,
el *pull* debe hacerse aplicando un *rebase*. Para ello debe usarse el comando:
```
git pull --rebase
```
Este comando iniciará un proceso de *rebase* entre desde la rama local hacia la
rama remota. Es decir, los *commit* locales no *pusheados* pasarán a tener como
padre el último *commit* remoto.

### 6.3. *Pull* con cambios locales no *commiteados*
En caso de que nos encontremos en medio de un *commit* (no se ha completado los
cambios necesarios para realizar un *commit*) y deseemos descargar nuevos
*commits* del servidor central, podemos hacerlo utilizando los comandos:
```
git stash
git pull --rebase
git stash pop
```


## 7. Tests.
Lo primero que se debe tener en cuenta a la hora de realizar tests es la existencia del módulo tests. Este proyecto está pensado para recoger las clases de utilidad que puedan ser compartidas por los tests de los distintos módulos que forman el proyecto. Por lo tanto, siempre que exista una clase o fichero que sea compartido por varios proyectos, debería almacenarse en este módulo.
En segundo lugar, es importante ser consciente de que, dependiendo del módulo en el que nos encontremos, deberemos hacer diferentes tipos de test.
Por último, como norma general, los métodos de prueba deben ser lo más sencillos posible, de modo que sea sencillo comprender qué es lo que se está evaluando.
## 7.1. Test por módulo.
entities: Tests de unidad para probar las entidades. Solo se realizarán tests sobre los constructores y los métodos con una cierta lógica, como pueden ser los métodos de las relaciones.
rest: Tests de integración con Hamcrest que añade expresividad a los test, HSQLDB que permite crear una base de datos en memoria,  Spring Test que controla el ciclo de vida de determinados tests, DbUnit que ofrece utilidades para la gestión de base de datos en tests y Spring Test DBUnit que permite utilizar DBUnit con anotaciones. Además de la lógica, deben testear la seguridad.
## 7.2 El módulo tests.
* **Clases Datasets**: Estas clases representan un conjunto de datos de pruebas. Contienen métodos para obtener a entidades que resultan de utilidad en los tests. Estas clases deben ubicarse en el mismo paquete que las entidades que contienen. El contenido de estas clases debe ser equivalente al contenido de los datasets de DBUnit que se describe a continuación.
* **Datasets DBUnit**: Los datasets DBUnit son representaciones en forma de XML de conjuntos de datos usados en los tests y pueden ser utilizados con las anotaciones de Spring @DatabaseSetup y @ExpectedDatabase. El contenido de estos ficheros debe ser el equivalente al de las clases dataset. Estos ficheros deben almacenarse en el directorio src/main/resources/datasets. 
* **Matchers Hamcrest para entidades**: Cada entidad debería tener un matcher de Hamcrest que permita compararla con otras entidades. Para facilitar el desarrollo de estos matchers se incluye la clase IsEqualsToEntity, que actúa como clase base para comparar dos entidades por sus propiedades. Las clases IsEqualsToUser e IsEqualsToRegistration sirven de ejemplo de como hacer esta implementación.

## 7.3 Ejecución de los tests.
## 7.3.1 Ejecución de los tests en Maven
Todos los tests del proyecto están configurados para ser ejecutados como tests normales y no como tests de integración. Esto significa que se pueden lanzar todos simplemente ejecutando el comando:
mvn install
## 7.4 Análisis de los resultados de los test
Cada vez que se ejecutan los tests se generará un fichero con información sobre los resultados. Concretamente, se generará el siguiente informe:
* **JUnit**: Genera informes sobre el éxito o fracaso de los tests. 

Estos informes se almacenan en <module>/target/surefire-reports. Son ficheros XML que pueden abrirse con Eclipse.


## 8. Guía de estilo
Un elemento importante para poder colaborar es que exista una uniformidad en el
código y otros elementos que forman parte del desarrollo. Esta sección sirve
como una pequeña guía de estilo que debe respetarse al trabajar en el proyecto.

### 8.1. Código fuente
Para uniformizar el código fuente deben respetarse las siguientes normas:
* **Idioma**: Todo el código (incluyendo la documentación) debe desarrollarse en
inglés.
* **Formato de código**: El código debe estar formateado, preferiblemente,
siguiendo la [Guía de Estilo para Java de Google](https://google.github.io/styleguide/javaguide.html)
o, al menos, utilizando el formato de código de Eclipse (`Ctrl`+`Mayus`+`F`).
* **Comentarios**: Debe evitarse **completamente** el código comentado y, en la
medida de lo posible, los comentarios en el código.
* **Documentación**: Todos los métodos públicos o protegidos deben incluir
documentación Javadoc. Se recomienda que se verifique que la documentación es
correcta utilizando el comando `mvn javadoc:javadoc`. Este comando generará la
documentación en formato HTML y fallará si encuentra algún error en la
documentación.

### 8.2. Control de versiones
Una de las bases de desarrollo que utilizaremos en este proyecto es el
**integrar tan pronto como se pueda**. Para ello, deben seguirse las siguientes
normas:
* **Contenido de los *commits***: Los *commits* deben ser completos en el
sentido de que no deben romper la construcción. Además, el código debe estar
probado, incluyendo los tests descritos en la [sección 7](#7-tests), para que el
resto de desarrolladores puedan confiar en el código. Es muy recomendable
revisar los informes de tests y de cobertura antes de hacer un *commit*.
* **Formato**: El formato de los *commits* deberá respetar las siguientes
normas:
  * Escritos en inglés.
  * Limitar el tamaño de línea a 80 columnas. Si se utiliza Eclipse, esto se
  hace de forma automática.
  * Primera línea descriptiva de lo que hace el *commit*:
    * Si está relacionado con alguna tarea concreta de las descritas en Kunagi,
    debe comenzar con el identificador de la tarea (p.ej. "tsk1 Adds...").
    * Si está relacionado con varias tareas, su número se separará con un guión
    (p.ej. "tsk1-2-13 Fixes...").
    * Debe estar redactada en tercera persona del presente (p.ej. *Adds...*,
      *Improves...*, *Modifies...*, etc.).
    * No debe llevar punto al final.
  * Cuerpo del *commit* descriptivo. Con una línea vacía de separación de la
  primera línea, debe escribirse un texto de explique claramente el trabajo
  hecho en el *commit*.
* **Frecuencia de *commit***: Los *commits* deben hacerse en pequeños pasos para
que la frecuencia sea alta. Para ello es recomendable desarrollar de una forma
ordenada, atacando partes concretas.
* **Frecuencia de *push***: Simpre que se haga un *commit* debe hacerse un
*push*. La única excepción a esta regla es que estemos haciendo pruebas locales
para evaluar una posible solución. En tal caso, es recomendable que esto se
haga en una rama independiente para evitar enviar *commits* accidentalmente a
la rama *develop* remota.


## 9. Instrucciones de ejecución

### 9.1. Ejecución con Maven
La configuración de Maven ha sido preparada para permitir varios tipos de
ejecución.

### 9.1.1. Ejecución de la aplicación con Tomcat y MySQL

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

### 9.1.2. Ejecución de la aplicación con Tomcat y MySQL con redespliegue automático

Durante el desarrollo es interesante que la apliación se redespliegue de forma
automática cada vez que se hace un cambio. Para ello podemos utilizar el
siguiente comand:

`mvn -Prun-tomcat-mysql -DskipTests=true package cargo:start fizzed-watcher:run`

La aplicación se servirá en la URL local: http://localhost:9080/letta

Para detener la ejecución podemos utilizar `Ctrl+C`.

### 9.2. Construcción con tests de unidad e integración

En esta construcción se ejecutarán todos los tests relacionados con el backend:

* **Unidad**: se utilizan para testear las entidades y las capas DAO y REST de
forma aislada.
* **Integración**: se utilizan para testear las capas REST y DAO de forma
integrada. Para este tipo de pruebas se utiliza una base de datos HSQL en
memoria.

El comando para lanzar esta construcción es:

`mvn install`

### 9.3. Construcción con tests de unidad, integración y aceptación

Esta construcción es similar a la previa, añadiendo las **pruebas de
aceptación**, que comprueban que las fucionalidades de la aplicación están
correctamente implementadas.

En estas pruebas se descarga y arranca el un servidor Tomcat 8 en el que se
despliega la aplicación configurada para utilizar una base de datos HSQL.

`mvn -Pacceptance-tests-cargo install`

