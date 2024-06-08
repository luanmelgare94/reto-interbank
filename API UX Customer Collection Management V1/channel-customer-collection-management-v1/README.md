# Channel Customer Collection Management

Microservicio Backend For Frontend que integra a los microservicios de informacion del cliente y sus productos financieros.

## Iniciando

Siga las siguientes instrucciones para iniciar el desarrollo de este proyecto.

### Pre-Requisitos

Plugins que deben estar instalados en su IDE:
* [Lombok](http://projectlombok.org/) - *Libreria de Bytecode que genera automaticamente los Getters y Setters*.

Ejemplo para levantar el api en Windows: mvn spring-boot:run -Drun.jvmArguments="-DLOGSTASH_SOCKET=pmbrklnxd11:9800"

## Flujo de desarrollo.

* Todo desarrollo debe iniciarse en una rama con la nomenclatura `feature/nombre-de-cambio` el cual debe crearse desde la rama `develop`.

* Cuando se completa el desarrollo, se deber? generar un `New Merge Request` desde la rama creada `feature/nombre-de-cambio` hacia la rama `develop`.

* Cuando los cambios son revisados y probados, se aceptar? el Merge Request, con lo que los cambios quedar?n listos en la rama `develop` para realizar el despliegue en el ambiente de desarrollo.


## Ejecuci?n de pruebas

Para la ejecuci?n de pruebas `unitarias` se debe ejecutar lo siguiente:

```
mvn test
```

Para la ejecuci?n de pruebas de `integraci?n` se debe ejecutar lo siguiente:

```
mvn verify -Dskip.integration.tests=false -Dskip.unit.tests=true
```
