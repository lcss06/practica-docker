# Practica de Docker

## Objetivo

Construir y ejecutar una API Java, un frontend React y una base de datos
MySQL en contenedores separados, sin conectar los componentes entre si.

## Requisitos

- Git.
- Docker Desktop iniciado y configurado para contenedores Linux.
- Conexion a internet para descargar imagenes y dependencias.

Java y Node se utilizan dentro de Docker; no es necesario instalarlos
en la computadora para construir estas imagenes.

## Componentes

| Componente | Contenedor | Puerto local | Puerto del contenedor |
|---|---|---|---|
| API Java | api-java | 8080 | 8080 |
| Frontend React con Nginx | frontend-react | 3000 | 80 |
| MySQL 8.4 | bd-mysql | 3307 | 3306 |

Los puertos se publican en 127.0.0.1 para acceso desde la propia computadora.

## Obtener el proyecto

```powershell
git clone https://github.com/lcss06/practica-docker.git
cd practica-docker
```

Los siguientes comandos se ejecutan desde la carpeta del proyecto.

## API Java

Construir la imagen:

```powershell
docker build -t practica-api:1.0 ./api
```

Crear y ejecutar el contenedor:

```powershell
docker run -d --name api-java -p 127.0.0.1:8080:8080 practica-api:1.0
```

Abrir http://localhost:8080/saludo

Respuesta esperada:

```json
{"mensaje":"Hola desde mi API Java"}
```

## Frontend React

Construir la imagen:

```powershell
docker build -t practica-frontend:1.0 ./frontend
```

La primera etapa del Dockerfile compila React con Node.
La segunda utiliza Nginx para servir los archivos generados.

Crear y ejecutar el contenedor:

```powershell
docker run -d --name frontend-react -p 127.0.0.1:3000:80 practica-frontend:1.0
```

Abrir http://localhost:3000

Se muestra la plantilla interactiva de React y Vite.
Los cambios en el codigo requieren reconstruir la imagen y recrear
el contenedor.

## Base de datos MySQL

Se utiliza una imagen oficial; no requiere un Dockerfile propio.

La siguiente clave es solo un ejemplo para esta practica local.

```powershell
docker run -d --name bd-mysql -p 127.0.0.1:3307:3306 -e MYSQL_ROOT_PASSWORD=PracticaLocal123 -e MYSQL_DATABASE=practica -v practica-mysql-datos:/var/lib/mysql mysql:8.4
```

Comprobar la inicializacion:

```powershell
docker logs --tail 30 bd-mysql
```

Esperar el mensaje "ready for connections" con el puerto 3306.

Acceder al cliente:

```powershell
docker exec -it bd-mysql mysql -u root -p
```

Introducir la clave definida al crear el contenedor y ejecutar:

```sql
SHOW DATABASES;
exit;
```

Debe aparecer la base de datos `practica`.

El volumen `practica-mysql-datos` conserva los datos al recrear el
contenedor. Las variables de inicializacion se aplican cuando el
directorio de datos esta vacio; no cambian la clave de una base existente.

## Administrar los contenedores

Ver contenedores activos:

```powershell
docker ps
```

Ver tambien los detenidos:

```powershell
docker ps -a
```

Detener los tres:

```powershell
docker stop api-java frontend-react bd-mysql
```

Volver a iniciar los contenedores existentes:

```powershell
docker start api-java frontend-react bd-mysql
```

`docker run` crea un contenedor nuevo. Si el contenedor ya existe,
utilizar `docker start` para iniciarlo.

## Conceptos practicados

- Imagen: paquete con el programa y lo necesario para ejecutarlo.
- Contenedor: instancia de una imagen.
- Dockerfile: instrucciones para construir una imagen.
- EXPOSE: documenta un puerto; no lo publica por si solo.
- -p: publica un puerto del contenedor en la computadora.
- Volumen: almacenamiento que persiste fuera del contenedor.
- README.md: documentacion escrita en Markdown.

## Trabajo con Git

Se practico un flujo basico de ramas:

- main: version estable de la practica.
- develop: integracion de los cambios.
- feature/...: desarrollo de una tarea especifica.

Se utilizaron add, commit, switch, merge y push.
