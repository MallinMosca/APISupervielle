# API REST
Servicio API REST creado para corresponder a un desafío técnico.
Autor: Mallin Mosca


## Contenidos
1. Información general
2. Tecnologías
3. Herramientas
4. Ejecución en forma local
5. Sobre el servicio

## Información General
***
Este proyecto fue creado bajo las consignas de un desafío técnico para un puesto de JAVA developer.

***
CONSIGNA DEL DESAFÍO TÉCNICO: 
***
Objetivo:
El principal objetivo es construir una API REST con las operaciones CRUD necesarias
para gestionar el recurso Persona.
Como objetivos secundarios tenemos algunas otras consignas desafiantes para que
puedas demostrar tu experiencia y conocimientos.
***
Consigna:
• No puede haber personas repetidas (las personas se identifican por Tipo de 
documento, número de documento, país y sexo).
• Las personas deben tener al menos un dato de contacto.
• No pueden crearse personas menores a 18 años.
• Pueden tener cualquier nacionalidad.
***
Desafíos:
#####Nivel 1: 
• Desarrollo que cumpla con la consiga inicial, que pueda descargarse 
y ejecutarse localmente.
• Documentación para configurar el entorno local.
#####Nivel 2: 
• API REST publicada en algún servicio free de cloud computing 
(Google App Engine, Heroku, Amazon AWS, Azure, etc)
• Agregar el recurso /estadisticas que devuelva cifras totalizadoras
de cantidad de mujeres, cantidad de hombres, porcentaje de 
argentinos sobre el total.
• Ejemplo:
{
“cantidad_mujeres”:”11240”, 
“cantidad_hombres”:”11200”,
“porcentaje_argentinos”:”98”
}
• Tener en cuenta que la API puede recibir fluctuaciones de tráfico 
(Entre 1 y 1100 request por segundo).
#####Nivel 3:
• Extender la API para que permita relacionar personas.
• Se debe crear el endpoint /personas/:id1/padre/:id2
▪ Debe soportar el método POST cuyo resultado es indicar que 
":id1" es padre de ":id2".
• Se debe crear el endpoint /relaciones/:id1/:id2
▪ Debe soportar el método GET que retorne la relación que 
existe entre ambas personas.
• Las relaciones posibles para retornar son:
{“HERMAN@”, “PRIM@”, “TI@”}
***
## Tecnologías

* Maven 4
* Java 11
* SpringBoot 
* Swagger

##Herramientas

*SpringBoot Initializr: Generador de proyectos de Spring con Gradle/Maven. Permite seleccionar las dependencias deseadas y genera automáticamente el scaffolding del proyecto.

*Postman: Cliente de APIs que nos permitirá realizar peticiones (y ver las respuestas) a la aplicación REST que vamos a construir.

## Ejecución de forma local

Hay varias formas de ejecutar una aplicación Spring Boot en su máquina local. Una forma es ejecutar el método main de la clase com.mallinmosca.supervielleAPI desde su IDE.

Alternativamente, puede usar el complemento Spring Boot Maven de la siguiente manera:
```
mvn spring-boot:run
```

## Sobre el servicio

Servicio REST que utiliza base de datos MySQL. En el caso de crear una base de datos de forma local asegurarse de modificar la configuración en application.properties.

El proyecto se encuentra desplegado en Google Cloud. 

#####Detalle:
Se puede ver el detalle de los servicios en:
 * (https://apispringsb.rj.r.appspot.com/swagger-ui/index.html#/)

 * (https://apispringsb.rj.r.appspot.com/v2/api-docs)

(Documentación creada con Swagger)
