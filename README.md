# INEGI

Este proyecto surgió de la necesidad de utilizar datos reales colectados por instituciones de México para integrarlos en
aplicaciones abstrayendo las complejidades del diseño y el acceso a los servicios del gobierno Mexicano.

En México el [Instituto Nacional de Estadística y Geografía](https://www.inegi.org.mx) es el encargado de medir,
colectar y publicar toda la información estadística de la sociedad. Mediante
el [API de Indicadores](https://www.inegi.org.mx/servicios/api_indicadores.html) permite consultar los datos y metadatos
de los indicadores disponibles a nivel nacional, por entidad federativa y municipio.

## Getting started

Este servicio está desarrollado en Spring Boot, para ejecutarlo localmente siga los siguientes pasos:

Clone el repositorio

```shell
git clone https://github.com/rubenqba/inegi.git
cd inegi
```

Compile el código

```shell
./mvnw package -DskipTests
```

Por último ejecute el servicio. Para poder acceder a los datos publicados por el INEGI se necesita un token que el
servicio tomará como variable de ambiente tal como se muestra a continuación. Para obtener el token se necesita
registrar [aquí](https://www.inegi.org.mx/app/desarrolladores/generatoken/Usuarios/token_Verify)

```shell
INEGI_TOKEN=secret-token java -jar inegi-service/target/inegi-service-1.0.0-SNAPSHOT.jar
```

Si el servicio se ejecuta correctamente estará disponible en el puerto `8080`. A continuación un ejemplo de request:

```http request
# obtiene el incremento porcentual de la inflación desde febrero de 2003 hasta mayo de 2005
GET http://localhost:8080/inflation/2003-02/2005-05
Accept: application/json
```

El request anterior obtendría una respuesta como la siguiente.

```http request
HTTP/1.1 200 OK
Connection: keep-alive
Transfer-Encoding: chunked
Content-Type: application/json
Date: Sat, 23 Mar 2024 06:08:07 GMT

{
  "initialPeriod": "2003-02",
  "finalPeriod": "2005-05",
  "value": 9.60
}
```

## Endpoints

TBD

## Docker deployment

TBD

