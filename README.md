# Lever Maker

Este software es un creador de videojuegos tanto para personas poco experimentadas en el mundo de la programación como para personas que quieran crear un videojuego con el motor.

# Uso de la aplicación

Aquí dividiremos a los usuarios que quieren hostear su propio servidor y los que quieren disfrutar de Lever Maker como un software normal.

## Los usuarios normales

### Acceder a una base de datos

Instalarán el software y se dirigirán arriba a la derecha. El círculo rojo de la imagen.

![](https://github.com/javiLeL/level-maker/blob/main/doc/imgs/screenshots/Captura1.PNG?raw=true)

Esto nos abrirá un nuevo apartado, el cual deberemos  rellenar con los datos de la base de datos que se va a utilizar para agregar la información.

Tras rellenar estos campos con los de la base de datos, presionaremos el botón guardar _(Esto también lo podemos hacer si queremos cambiar de base de datos)_.

### Registrarse

Tras introducir los datos de la base de datos, deberemos registrarnos si es que no lo estamos. Para ello, nos seleccionaremos la opción de registrarnos.

![](https://github.com/javiLeL/level-maker/blob/main/doc/imgs/screenshots/Captura3.PNG?raw=true)

Tras esto nos aparecera un formulario el cual deberemos rellenar, tras esto seleccionaremos el boton de registratse y si todo va bien nos habremos registrado.

Una vez registrados cuando queramos volver ha acceder a nuestro contenido solo debemos iniciar sesión.

### Iniciar sesión

Para ello deberemos simplemente reiniciar el programa si ya estamos con una sesión iniciada o salir del apartado donde nos encontramos y dirigirnos al apartado de inicio de sesión y rellenaremos los datos que nos pide.

### Creación y Edición de Niveles

Tras acceder a nuestro contenido, si está vacío, solo podremos crear nuevos niveles. Al hacer esto, nos permitirá poner nombre al nivel. Escribiremos el nombre de nuestra elección, crearemos el nivel para posteriormente editarlo.
Después de hacer esto entraremos en el modo de edición en el cual podemos elegir bloques y ponerlos en el mapa.

#### **Controles**

Para poder moverse por el modo edito es necesario tener los controles en mente, estos son:

| Tecla                         | Accion                           |
| ----------------------------- | -------------------------------- |
| `r`                           | Rota 90° el bloque seleccionado. |
| `click izquierdo`             | Colocal un bloque seleccionado   |
| `click derecho`               | Borrar un bloque ya colocado     |
| `click central` + `arrastrar` | Permite mover la cámara          |
| `esc`                         | Salir del **modo creacción**     |

Para salir del modo editor seleccionaremos el boton de arriba a la izquierda y seleccionaremos la opccion guardar y salir. Tal y como se muestra en la presentación:

![](https://github.com/javiLeL/level-maker/blob/main/doc/videos/modo-editor.gif?raw=true)

### Jugar Niveles

Tras crear un nivel podremos jugarlo para ello lo seleccionaremos y presionaremos jugar nivel esto nos abrira el **Modo Juego** el cual cargara todas las colisiones del nivel creado.

#### **Controles**

| Tecla     | Accion                   |
| --------- | ------------------------ |
| `a`       | Moverse a la izquierda   |
| `d`       | Moverse a la derecha     |
| `espacio` | Saltar                   |
| `esc`     | Salir del **modo juego** |

Pequeño ejemplo de cómo se ve el modo juego:

![](https://github.com/javiLeL/level-maker/blob/main/doc/videos/modo-juego.gif?raw=true)

### Borrar niveles

Si deseamos borrar un nivel, lo seleccionaremos y presionaremos el botón borrar. Este no se borrará de inmediato ya que por motivos de seguridad debemos presionar el botón aceptar 2 veces.

## Los usuarios avanzados

Se entenderá a usuarios avanzados como personas que poseen ciertos conocimientos con la programación o mínimo uso de bases de datos, si no es tu caso y, aun así, quieres hacerlo, puede apoyarte con varios tutoriales para lograrlo.

Estos deberán  usar un soporte de base de datos, ya sea `MySQL` o compatibles con este como `mariaDB`, y ejecutar los archivos SQL que se encuentran en la carpeta [SQL](https://github.com/javiLeL/lever-maker/tree/main/src/db/sql) . Esto creará las tablas necesarias para el correcto funcionamiento de la base de datos.

Para que los demás usuarios se puedan conectar es necesario abrir un puerto o solo funcionar en local.
