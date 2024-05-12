# Level - Maker

## 1. Introdución

El proyecto consistirá en un sistema de desarrollo de videojuegos de tipo plataforma.

En este el usuario podrá crear un nivel y jugarlo. Todo esto se realizara mediante un programa de escritorio.

La principal motivación es poder llevar el desarrollo de videojuegos a personas que no están familiarizadas con el mundo de los videojuegos o la programación, gracias a este software todas estas personas tendrán un acceso más amigable y con ello se les facilitara el crear un título.

Este motor usara una base de datos para guardar los niveles de los creadores, para ello los usuarios deberán de registrarse para posteriormente acceder, crear y borrar los diferentes niveles creados por ellos.

## 2. Requisitos

- El software hará uso de un sistema multiusuario para poder albergar varios clientes.

- Modos lo más esencial del software ya que el usuario será donde más tiempo pasara al usarlo, estos son dos:

    - Modo *Creación*: En este modo el usuario podrá crear sus niveles con la ayuda de una barra lateral situada a la izquierda con todos los bloques que este puede poner, al seleccionar uno de estos y clicar dentro del propio nivel se creara una celda en la cuadricula con el bloque anteriormente seleccionado. Repitiendo esto sucesivamente podrá crear el mundo que tiene presente en su cabeza.

    - Modo Juego: En este modo el jugador podrá jugar el nivel creado en el modo Creación esto lo hará con inputs del teclado con WASD para la dirección y la tecla espacio para saltar (esto de forma  redeterminada) y con esto podrá moverse dentro del mundo que el mismo ha creado.
    - 
- Se dispondrá de un menú de opciones para poder modificar de forma gráfica y sencilla ciertos parámetros del nivel como la gravedad, fondo, etc…

- También se usara un menú de opciones generales para poder ajustar el tamaño de la pantalla, FPS y en general lograr hacer que la instancia del usuario sea lo más personalizada posible.

### 2.1 Requisitos Funcionales

| ID  | Requisito                                                                                                                 | Priorodad |
| --- | ------------------------------------------------------------------------------------------------------------------------- | --------- |
| F01 | El usuario se registrara con correo, contraseña y nombre de usuario de forma obligatoria.                                 | ALTA      |
| F02 | Crear el motor sobre el estará montada toda la GUI del programa.                                                          | ALTA      |
| F03 | Crear la **base** de datos que usara el programa para trabajar.                                                           | ALTA      |
| F04 | Crear cada uno de los modos                                                                                               | ALTA      |
| F05 | Añadir texturas variadas al motor para que los usuario puedan tener una gran variedad de bloques para decorar sus niveles | BAJA      |
| F06 | Añadir una GUI agradable al usuario y fácilmente entendible                                                               | MEDIA     |
| F07 | Añadir la implementación de MODs (modificaciones externas)                                                                | BAJA      |
| F08 | El usuario pude meter su propios assets                                                                                   | BAJA      |
| F09 | El usuario pueda dar de baja su cuenta                                                                                    | BAJA      |
| F10 | Compartir mundos entre usuario                                                                                            | BAJA      |
| F11 | Hacer una buena documentación                                                                                             | ALTA      |

### 2.2 Requisitos No Funcionales

| ID  | Requisito                  | Solución                                                                                                                                                                                                        |
| --- | -------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| N01 | Segurida                   | Para brindar seguridad a los usuarios se guardaran las contraseñas cifradas al igual que el contenido que el usuario este guardando en la base de datos para que cualquier intento de robo de datos sea en vano |
| N02 | Rendimiento                | Que el programa este bien optimizado.                                                                                                                                                                           |
| N03 | Estabilidad                | Para esto se verán todas las posibles excepciones para poder dar una mejor experiencia de usuario.                                                                                                              |
| N04 | Personalización            | Con esto se permitirá que la instancia de los usuarios se más agradable.                                                                                                                                        |
| N05 | Buen Manejo de Excepciones | Esto para que los usuarios no se confundan y/o malinterpreten errores de funcionamiento del programa.                                                                                                           |
| N06 | Portabilidad               | Ya que el lenguaje de programación es JAVA este tiene la capacidad de poder ser ejecutado en muchos sistemas operativos.                                                                                        |
| N07 | Reusabilidad               | Ya que es una herramienta de carácter creativo esta es muy versátil y al no tener un fin establecido, hace que se reutilice en el tiempo.                                                                       |

## 3. Diseño de la aplicación

### 3.1 Casos de uso 

Este diagrama hace una representación de todas las funcionalidades que puede realizar el usuario todas ellas necesitaran de acceso a internet para que se realicen de forma correcta:

![](https://raw.githubusercontent.com/javiLeL/Lever-Maker/main/doc/imgs/Diagrama%20de%20uso.png?token=GHSAT0AAAAAACQMXCNAR73J3WATOJIA4PPYZRAJOGQ)

| Id   | Nombre         | Prioridad |
| ---- | -------------- | --------- |
| CU01 | Registrar      | MEDIO     |
| CU02 | Iniciar sesión | MEDIO     |
| CU03 | Crear nivel    | ALTA      |
| CU04 | Editar nivel   | ALTA      |
| CU05 | Borrar nivel   | BAJA      |
| CU06 | Jugar nivel    | ALTA      |

### 3.2 Diagrama Entidad-Relación

Este sería un diagrama para ver de forma visual de cómo se organizara la base de datos del proyecto:

![](https://raw.githubusercontent.com/javiLeL/Lever-Maker/main/doc/imgs/Diagrama%20Entidad%20Relacion.png?token=GHSAT0AAAAAACQMXCNBM7TEQ56IMHOD2EUYZRAJOEQ)

Del usuario se guardar el correo y la contraseña ya que para iniciar sesión solo harán falta estos dos campo, el correo se guardara tal cual y la contraseña se pasara por md5 para hacer que ocupe exactamente 32 caracteres.

Por otro lado tenemos la tabla de información de usuario esta tabla será complementara a la anterior esto es por si se desease en un futuro recopilar más información del usuario se podría modificar solo esta tabla.

Sobre la tabla del nivel esta guardara información relacionada con los niveles creados por el usuario, un usuario podrá poseer varios niveles y para poder acceder a estos se buscaran los niveles que su foreing key sea igual a la de su correo electrónico. Este usara un sistema de id auto incremental para que el
usuario no tenga que escribir su id con respecto a la data, desde java se usara Serializabe para pasar todo cargado en un nivel para transformarlo a un String y ese será el que se almacene dentro. Al cargar un nivel se cargara ese String y volverá a funcionar como si nada hubiera pasado.