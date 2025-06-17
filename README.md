# 👾 Lever Maker

Motor ligero para videojuegos de plataformas, con soporte multiusuario, que permite crear, gestionar y jugar niveles.

## 📝 Descripción

Este software está diseñado para permitir la creación de videojuegos de tipo plataformas tanto a principiantes, sin experiencia en programación, como a desarrolladores que desean construir sus propios juegos utilizando el motor.

## 🚀 Características

-   ✅ Creación y edición de **niveles**.
-   👥 Soporte **multiusuario**.
-   📊 Funciona apartir de una base de datos _(auto aloja o publica)_.
-   🔑 Datos **privados**.
-   👾 Capacidad de **jugar** tus propios niveles.
-   🪟 Interfaz creada desde **cero**.

    > Esto incluye
    >
    > -   Botones
    > -   Cajas de inserción de texto
    > -   Sidebars
    > -   Etc...

-   📍 Se usa un sistema **CRUD** flexible.

## 🛠️ Tecnologías utilizadas

-   Lenguaje: **Java**.
-   Almacenamiento: **MYSql**, **MariaDB**...
-   Hash de contraseñas: **MD5**.

## 📦 Instalación

### Construir por ti mismo

1. Clona este repositorio:

```sh
git clone https://github.com/RacoonByte01/level-maker.git
```

2. Compilar

    - Como un binario de java _(`.class`)_:

        ```sh
        cd level-maker
        mkdir bin
        cd src
        javac -d ../bin -cp "libs/mysql-connector-j-8.0.33.jar:" main/Main.java
        ```

        Ejecutar:

        ```sh
        cp -r assets ../bin
        cp db/db.cfg ../bin/db
        cd ../bin
        java -cp "../src/libs/mysql-connector-j-8.0.33.jar:" main/Main
        ```

        > [!warning]
        > Si se usa un sistema **DOS** cambiar el caracter `:` por el caracter `;`.

    - Como un comprimido de java _(`.jar`)_:

        Solo es necesario compilarlo no ejecutarlo.

        ```sh
        cd ../bin
        mkdir -p ../build/db
        cp -r ../src/libs ../build
        cp -r ../src/assets ../build
        cp ../src/db/db.cfg ../build/db
        jar -cvfm ../build/lever-maker.jar ../MANIFEST.MF *
        ```

### [Usar el compilado](https://github.com/RacoonByte01/level-maker/releases/tag/v1.2)
