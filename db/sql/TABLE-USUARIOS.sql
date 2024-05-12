CREATE TABLE USUARIOS(
    -- We must use VARCHAR(200) because MySql dont read TEXT like primary key
    CORREO VARCHAR(200) NOT NULL PRIMARY KEY,
    -- VARCHAR(32) because we use MD5
    PASS VARCHAR(32) NOT NULL
);